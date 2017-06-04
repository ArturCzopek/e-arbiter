package pl.cyganki.tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.cyganki.tournament.model.CodeTask;
import pl.cyganki.tournament.model.QuestionTask;
import pl.cyganki.tournament.model.Task;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.repository.CodeTaskRepository;
import pl.cyganki.tournament.repository.QuestionTaskRepository;
import pl.cyganki.tournament.repository.TaskRepository;
import pl.cyganki.tournament.repository.TournamentRepository;

import java.util.*;

@Profile("dev")
@Component
public class SampleDataLoader implements ApplicationRunner {

    private CodeTaskRepository codeTaskRepository;
    private TournamentRepository tournamentRepository;
    private QuestionTaskRepository questionTaskRepository;
    private TaskRepository taskRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public SampleDataLoader(CodeTaskRepository codeTaskRepository, TournamentRepository tournamentRepository,
                            QuestionTaskRepository questionTaskRepository, TaskRepository taskRepository,
                            MongoTemplate mongoTemplate) {
        this.codeTaskRepository = codeTaskRepository;
        this.tournamentRepository = tournamentRepository;
        this.questionTaskRepository = questionTaskRepository;
        this.taskRepository = taskRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        mongoTemplate.getDb().dropDatabase();

        List <QuestionTask.Answer> questionsTasksAnswersList = Arrays.asList(
                QuestionTask.Answer.builder().content("Answer 1").value(true).build(),
                QuestionTask.Answer.builder().content("Answer 2").value(true).build(),
                QuestionTask.Answer.builder().content("Answer 3").value(false).build()
        );

        List <QuestionTask> questionTaskList = Arrays.asList(QuestionTask.builder().questionId(1).pointForQuestionTask(5).content("Testowe pytanie es numero 1").answers(questionsTasksAnswersList).build(),
                QuestionTask.builder().questionId(2).pointForQuestionTask(5).content("Testowe pytanie es numero 2").answers(questionsTasksAnswersList).build()
        );

        List<String> parameters = Arrays.asList(
                "Jeden",
                "Dwa",
                "Trzy"
        );

        List<CodeTask.TestSet> codesTasksTestSetsList = Arrays.asList(
                CodeTask.TestSet.builder().result("Result 1").parameters(parameters).build(),
                CodeTask.TestSet.builder().result("Result 2").parameters(parameters).build()
        );

        List <CodeTask> codesTasksList = Arrays.asList(
                CodeTask.builder().codeTaskId(1).testSets(codesTasksTestSetsList).language(CodeTask.Language.PYTHON).pointsForCodeTask(5).build(),
                CodeTask.builder().codeTaskId(2).testSets(codesTasksTestSetsList).language(CodeTask.Language.JAVA).pointsForCodeTask(7).build(),
                CodeTask.builder().codeTaskId(3).testSets(codesTasksTestSetsList).language(CodeTask.Language.CPP).pointsForCodeTask(2).build()
        );


        List<Task> tasksList = Arrays.asList(
                Task.builder().taskId(1).codeTasks(codesTasksList).questionTasks(questionTaskList).build(),
                Task.builder().taskId(2).codeTasks(codesTasksList).questionTasks(questionTaskList).build(),
                Task.builder().taskId(3).codeTasks(codesTasksList).questionTasks(questionTaskList).build()
        );


        List<Tournament> tournamentsList = Arrays.asList(
                Tournament.builder().tournamentId(1).ownerId(34).name("Tournament 1").publicFlag(true).tasks(tasksList).maxPoints(100).build(),
                Tournament.builder().tournamentId(2).ownerId(32).name("Tournament 2").publicFlag(false).tasks(tasksList).maxPoints(34).build()
        );

        questionTaskRepository.save(questionTaskList);

        codeTaskRepository.save(codesTasksList);

        taskRepository.save(tasksList);

        tournamentRepository.save(tournamentsList);
    }
}