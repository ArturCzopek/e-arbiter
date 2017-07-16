package pl.cyganki.tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.cyganki.tournament.repository.TaskRepository;
import pl.cyganki.tournament.repository.TournamentRepository;

@Profile({"dev", "test"})
@Component
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
        /*mongoTemplate.getDb().dropDatabase();
        List<Answer> questionsTasksAnswersList = Arrays.asList(
                Answer.builder().content("Answer 1").correct(true).build(),
                Answer.builder().content("Answer 2").correct(true).build(),
                Answer.builder().content("Answer 3").correct(false).build()
        );

        List<String> parameters = Arrays.asList(
                "Jeden",
                "Dwa",
                "Trzy"
        );

        List<TestSet> codesTasksTestSetsList = Arrays.asList(
                new TestSet("10152025", Arrays.asList("10", "15", "20", "25")),
                new TestSet("Tak!", Arrays.asList("T", "a", "k", "!"))
                //TestSet.builder().expectedResult("Result 1").parameters(parameters).build(),
                //TestSet.builder().expectedResult("Result 2").parameters(parameters).build()
        );

        List<CodeTask.Language> languages = Arrays.asList(
                CodeTask.Language.PYTHON,
                CodeTask.Language.JAVA,
                CodeTask.Language.CPP
        );

        List<Task> tasksList = Arrays.asList(
                new CodeTask(codesTasksTestSetsList, languages, 50, 10),
                new CodeTask(codesTasksTestSetsList, languages, 20, 15)
                //CodeTask.builder().taskId(1).maxPoints(5).testSets(codesTasksTestSetsList).languages(languages).build(),
                //CodeTask.builder().taskId(2).maxPoints(3).testSets(codesTasksTestSetsList).languages(languages).build(),
                //Question.builder().taskId(3).maxPoints(10).content("Testowe pytanie es numero 1").answers(questionsTasksAnswersList).build(),
                //Question.builder().taskId(4).maxPoints(20).content("Testowe pytanie es numero 2").answers(questionsTasksAnswersList).build()
        );

        List<Tournament> tournamentsList = Arrays.asList(
                new Tournament(36L, "TT1", LocalDate.of(2017, 7, 22), true, tasksList),
                new Tournament(38L, "TT2", LocalDate.of(2017, 7, 28), false, tasksList)
                //Tournament.builder().tournamentId(1).ownerId(34).name("Tournament 1").publicFlag(true).tasks(tasksList).maxPoints(100).build(),
                //Tournament.builder().tournamentId(2).ownerId(32).name("Tournament 2").publicFlag(false).tasks(tasksList).maxPoints(34).build()
        );

        tournamentRepository.save(tournamentsList);*/
    }
}