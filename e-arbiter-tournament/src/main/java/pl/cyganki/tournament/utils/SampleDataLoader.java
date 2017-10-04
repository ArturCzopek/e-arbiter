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

/**
 * Only known solution for now for using the same id every time is to generate own 24-length string.
 * We do it by generating id 1, 2, 3 etc, and then, string is filled by "0"'s
 * For now, ids for tasks (no of tournament -> task IDS!!! not filled by 0's yet):
 *
 * DRAFT:
 * 1 -> 1 to 7
 * 2 -> 8 to 15
 * 3 -> 16 to 30
 * 4 -> 31 to 35
 * 5 -> 36 to 39
 * 6 -> 40 to 48
 * 7 -> 49 to 56
 * 8 -> 57 to 63
 * 9 -> 64 to 81
 * 10 -> 82 to 93
 *
 * ACTIVE:
 * 1 -> 94 to 102
 * 2 -> 103 to 108
 * 3 -> 109 to 122
 * 4 -> 123 to 130
 * 5 -> 131 to 137
 * 6 -> 138 to 148
 * 7 -> 149 to 156
 * 8 -> 157 to 169
 * 9 -> 170 to 175
 * 10 -> only 176
 *
 * FINISHED:
 * 1 -> 177 to 183
 * 2 -> 184 to 190
 * 3 -> 191 to 205
 * 4 -> 206 to 219
 * 5 -> 220 to 238
 * 6 -> 239 to 243
 * 7 -> 244 to 249
 * 8 -> 250 to 255
 * 9 -> 256 to 261
 * 10 -> 262 to 266
 */

@Profile({"dev", "test"})
@Component
@Slf4j
public class SampleDataLoader implements ApplicationRunner {

    private MongoTemplate mongoTemplate;
    private TournamentRepository tournamentRepository;
    private ObjectMapper mapper;

    private static final String ZERO_HEX_VALUE = "000000000000000000000000";
    private static int CURRENT_NEW_TEST_TASK_ID = 1;

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
                tournament.getTasks().forEach(task -> task.setId(getTestId()));
                tournamentRepository.save(tournament);
            } catch (Exception e) {
                throw new RuntimeException("Cannot insert tournament. Current index: " + i + ", folder: " + tournamentFolderName + "; " + e.getMessage());
            }
        }
    }

    private String getTestId() {
        String newId = ZERO_HEX_VALUE + CURRENT_NEW_TEST_TASK_ID++;
        int diff = newId.length() - ZERO_HEX_VALUE.length();
        return newId.substring(diff);
    }
}