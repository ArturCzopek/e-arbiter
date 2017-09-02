package pl.cyganki.tournament.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.repository.TournamentRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Profile({"dev", "test"})
@Component
@Slf4j
public class SampleDataLoader implements ApplicationRunner {

    private MongoTemplate mongoTemplate;
    private TournamentRepository tournamentRepository;
    private ObjectMapper mapper;

    @Autowired
    public SampleDataLoader(MongoTemplate mongoTemplate, TournamentRepository tournamentRepository) {
        this.mongoTemplate = mongoTemplate;
        this.tournamentRepository = tournamentRepository;
        this.mapper = new ObjectMapper();
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        mongoTemplate.getDb().dropDatabase();

        try {
            insertTournament("draft", 10);
            insertTournament("active", 10);
            insertTournament("finished", 10);
            log.info("Dropped Tournaments table and created new tournaments");
        } catch (Exception e) {
            log.error("Cannot insert test tournaments! {}", e.getMessage());
        }
    }

    private void insertTournament(String tournamentFolderName, int tournamentsToInsert) throws IOException {

        for (int i = 1; i <= tournamentsToInsert; i++) {
            InputStream tournamentStream;
            try {
                String jsonPath = "/db/changelog/test-data/" + tournamentFolderName + "/" + tournamentFolderName + "-tournament-" + i + ".json";
                tournamentStream = TypeReference.class.getResourceAsStream(jsonPath);
            } catch (NullPointerException e) {
                // there is less files. Just break and let us know about it
                log.warn("There is less tournaments than expected. Expected: {}, found: {}", tournamentsToInsert, i - 1);
                break;
            }

            Scanner s = new Scanner(tournamentStream).useDelimiter("\\A");
            String tournamentsJSON = s.hasNext() ? s.next() : "";

            try {
                Tournament tournament = mapper.readValue(tournamentsJSON, Tournament.class);
                tournamentRepository.save(tournament);
            } catch (Exception e) {
                throw new RuntimeException("Cannot insert tournament. Current index: " + i + ", folder: " + tournamentFolderName + "; " + e.getMessage());
            }
        }
    }
}