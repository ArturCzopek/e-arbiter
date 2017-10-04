package pl.cyganki.tournament.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.cyganki.tournament.model.CodeTask;
import pl.cyganki.tournament.model.CodeTaskTestSet;
import pl.cyganki.tournament.model.Task;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.utils.modules.ExecutorModuleInterface;
import pl.cyganki.utils.modules.executor.model.ExecutionRequest;
import pl.cyganki.utils.modules.executor.model.ExecutionResult;
import pl.cyganki.utils.modules.tournament.model.Language;

@Service
public class TaskService {

    private final TournamentService tournamentService;
    private final ExecutorModuleInterface executorModuleInterface;

    @Autowired
    public TaskService(TournamentService tournamentService, ExecutorModuleInterface executorModuleInterface) {
        this.tournamentService = tournamentService;
        this.executorModuleInterface = executorModuleInterface;
    }

    public ExecutionResult submitCode(String tournamentId, String taskId,
                                      byte[] program, Language language) {

        final CodeTask task = (CodeTask) findTask(tournamentId, taskId);

        if (task == null) {
            return new ExecutionResult(ExecutionResult.Status.FAILURE, "Bad request.");
        }

        return executorModuleInterface.execute(new ExecutionRequest(program, language.getExtension(), getCodeTestData(task)));
    }

    public Task findTask(String tournamentId, String taskId) {
        final Tournament tournament = tournamentService.findById(tournamentId);
        Task task = null;

        if (tournament != null) {
            task = tournament.getTasks().stream()
                    .filter(t -> taskId.equals(t.getId()))
                    .findFirst()
                    .orElse(null);
        }

        return task;
    }

    private byte[] getCodeTestData(CodeTask task) {
        StringBuilder sb = new StringBuilder();

        for (CodeTaskTestSet testSet: task.getCodeTaskTestSets()) {
            sb.append(String.join(" ", testSet.getParameters()));
            sb.append(" ");
            sb.append(testSet.getExpectedResult());
            sb.append("\n");
        }

        return sb.toString().getBytes();
    }

}
