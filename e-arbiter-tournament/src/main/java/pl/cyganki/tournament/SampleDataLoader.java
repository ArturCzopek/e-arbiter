package pl.cyganki.tournament;

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
import pl.cyganki.tournament.repository.TaskRepository;
import pl.cyganki.tournament.repository.TournamentRepository;

@Profile({"dev", "test"})
@Component
@Slf4j
public class SampleDataLoader implements ApplicationRunner {

    private TournamentRepository tournamentRepository;
    private TaskRepository taskRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public SampleDataLoader(TournamentRepository tournamentRepository, TaskRepository taskRepository,
                            MongoTemplate mongoTemplate) {
        this.tournamentRepository = tournamentRepository;
        this.taskRepository = taskRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        mongoTemplate.getDb().dropDatabase();

        final String sampleJson = "{  \n" +
                "   \"ownerId\":36,\n" +
                "   \"name\":\"Sample Tournament\",\n" +
                "   \"description\":\"This is an example of how Tournament's JSON representation looks like.\",\n" +
                "   \"startDate\":null,\n" +
                "   \"endDate\":[  \n" +
                "      2017,\n" +
                "      7,\n" +
                "      22\n" +
                "   ],\n" +
                "   \"publicFlag\":true,\n" +
                "   \"joinedUsersId\":null,\n" +
                "   \"tasks\":[  \n" +
                "      {  \n" +
                "         \"@type\":\"CodeTask\",\n" +
                "         \"description\":\"Sample CodeTask description. Just for testing purposes.\",\n" +
                "         \"codeTaskTestSets\":[  \n" +
                "            {  \n" +
                "               \"expectedResult\":\"100150200250\",\n" +
                "               \"parameters\":[  \n" +
                "                  \"100\",\n" +
                "                  \"150\",\n" +
                "                  \"200\",\n" +
                "                  \"250\"\n" +
                "               ]\n" +
                "            },\n" +
                "            {  \n" +
                "               \"expectedResult\":\"TAK!\",\n" +
                "               \"parameters\":[  \n" +
                "                  \"T\",\n" +
                "                  \"A\",\n" +
                "                  \"K\",\n" +
                "                  \"!\"\n" +
                "               ]\n" +
                "            }\n" +
                "         ],\n" +
                "         \"languages\":[  \n" +
                "            \"PYTHON\",\n" +
                "            \"JAVA\",\n" +
                "            \"CPP\"\n" +
                "         ],\n" +
                "         \"timeoutInMs\":50\n" +
                "      },\n" +
                "      {  \n" +
                "         \"@type\":\"QuizTask\",\n" +
                "         \"name\":\"Sample Quiz\",\n" +
                "         \"questions\":[  \n" +
                "            {  \n" +
                "               \"content\":\"Question's Content\",\n" +
                "               \"answers\":[  \n" +
                "                  {  \n" +
                "                     \"content\":\"1st answer's content\",\n" +
                "                     \"correct\":true\n" +
                "                  },\n" +
                "                  {  \n" +
                "                     \"content\":\"2nd answer's content\",\n" +
                "                     \"correct\":false\n" +
                "                  }\n" +
                "               ]\n" +
                "            }\n" +
                "         ]\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        Tournament tournament = mapper.readValue(sampleJson, Tournament.class);

        tournamentRepository.save(tournament);

        log.info("Dropped Tournament table and created a new one with one tournament");
    }
}