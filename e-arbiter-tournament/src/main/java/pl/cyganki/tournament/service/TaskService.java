package pl.cyganki.tournament.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.cyganki.tournament.model.CodeSubmitForm;
import pl.cyganki.tournament.model.CodeTask;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.utils.modules.ExecutorModuleInterface;
import pl.cyganki.utils.modules.executor.model.ExecutionRequest;
import pl.cyganki.utils.modules.executor.model.ExecutionResult;

@Service
public class TaskService {

    private static final ExecutionResult BAD_REQUEST_RESULT =
            new ExecutionResult(ExecutionResult.Status.FAILURE, "Bad request.");

    private final TournamentManagementService tournamentManagementService;
    private final ExecutorModuleInterface executorModuleInterface;

    @Autowired
    public TaskService(TournamentManagementService tournamentManagementService, ExecutorModuleInterface executorModuleInterface) {
        this.tournamentManagementService = tournamentManagementService;
        this.executorModuleInterface = executorModuleInterface;
    }

    public ExecutionResult submitCode(Long userId, CodeSubmitForm csf) {

        final Tournament tournament = tournamentManagementService.findTournamentByIdAndJoinedUserId(csf.getTournamentId(), userId);

        if (tournament == null) {
            return BAD_REQUEST_RESULT;
        }

        final CodeTask codeTask = (CodeTask) tournament.getTasks().stream()
                .filter(task -> task.getId().equals(csf.getTaskId()))
                .findFirst()
                .orElse(null);

        if (codeTask == null) {
            return BAD_REQUEST_RESULT;
        }

        return executorModuleInterface.execute(new ExecutionRequest(csf.getProgram().getBytes(), csf.getLanguage().getExtension(), codeTask.getCodeTaskData()));
    }

}
