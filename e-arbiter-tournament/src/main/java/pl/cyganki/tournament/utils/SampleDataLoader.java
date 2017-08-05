package pl.cyganki.tournament.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Scanner;

@Profile({"dev", "test"})
@Component
@Slf4j
public class SampleDataLoader implements ApplicationRunner {

    private MongoTemplate mongoTemplate;

    @Autowired
    public SampleDataLoader(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        mongoTemplate.getDb().dropDatabase();

        try {
            insertTournament("draft-tournament");
            insertTournament("active-tournament");
            log.info("Dropped Tournaments table and created new tournaments");
        } catch (Exception e) {
            log.error("Cannot insert test tournaments! {}", e.getMessage());
        }
    }

    private void insertTournament(String tournamentFileName) {
        InputStream tournamentsStream = TypeReference.class.getResourceAsStream("/db/changelog/test-data/" + tournamentFileName + ".json");
        Scanner s = new Scanner(tournamentsStream).useDelimiter("\\A");
        String tournamentsJSON = s.hasNext() ? s.next() : "";
        DBObject tournamentsDBO = (DBObject) JSON.parse(tournamentsJSON);
        mongoTemplate.getDb().getCollection("TOURNAMENTS").insert(tournamentsDBO);
    }
}