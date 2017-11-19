package pl.cyganki.tournament.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.cyganki.tournament.model.*;
import pl.cyganki.tournament.repository.TournamentRepository;
import pl.cyganki.utils.model.executor.ExecutionRequest;
import pl.cyganki.utils.model.executor.ExecutionResult;
import pl.cyganki.utils.model.tournamentresults.CodeTaskResultDto;
import pl.cyganki.utils.model.tournamentresults.QuizTaskResultDto;
import pl.cyganki.utils.modules.ExecutorModuleInterface;
import pl.cyganki.utils.modules.TournamentResultsModuleInterface;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TaskService {

    private static final ExecutionResult BAD_REQUEST_RESULT =
            new ExecutionResult(ExecutionResult.Status.FAILURE, "Cannot execute code for given task.");

    private final TournamentRepository tournamentRepository;
    private final ExecutorModuleInterface executorModuleInterface;
    private final TournamentResultsModuleInterface tournamentResultsModuleInterface;

    @Autowired
    public TaskService(TournamentRepository tournamentRepository, ExecutorModuleInterface executorModuleInterface,
                       TournamentResultsModuleInterface tournamentResultsModuleInterface) {
        this.tournamentRepository = tournamentRepository;
        this.executorModuleInterface = executorModuleInterface;
        this.tournamentResultsModuleInterface = tournamentResultsModuleInterface;
    }

    public Task getTaskById(long userId, String tournamentId, String taskId) {
        final Optional<Tournament> tournament = Optional.ofNullable(
                tournamentRepository.findActiveTournamentInWhichUserParticipatesById(tournamentId, userId));

        if (tournament.isPresent()) {
            return tournament.get().getTask(taskId);
        }

        return null;
    }

    public ExecutionResult submitCode(long userId, CodeSubmitForm csf) {

        final CodeTask codeTask = findCodeTaskToExecute(userId, csf);

        if (codeTask == null) {
            return BAD_REQUEST_RESULT;
        }

        final ExecutionResult executionResult = executorModuleInterface
                .execute(new ExecutionRequest(csf.getProgramBytes(), csf.getLanguageExtension(), codeTask.getCodeTaskData(), codeTask.getTimeoutInMs()));

        final CodeTaskResultDto codeTaskResultDto =
                TaskResultDtoBuilder.fromCodeTaskResult(userId, codeTask, csf, executionResult);

        this.tournamentResultsModuleInterface.saveCodeTaskResult(codeTaskResultDto);
        return executionResult;
    }

    public QuizTaskResultDto submitQuiz(long userId, QuizSubmission quizSubmission) {
        final QuizTask quizTask = findQuizTaskToValidate(userId, quizSubmission);

        final QuizTaskResultDto quizTaskResultDto = new QuizTaskResultDto(
                userId,
                quizSubmission.getTournamentId(),
                quizSubmission.getTaskId(),
                getQuizEarnedPoints(quizTask, quizSubmission.getQuestions()));

        this.tournamentResultsModuleInterface.saveQuizTaskResult(quizTaskResultDto);
        return quizTaskResultDto;
    }

    private String getQuizEarnedPoints(QuizTask quizTask, List<Question> userSubmission) {
        final List<Question> originalQuestions = quizTask.getQuestions();

        StringBuilder earnedPoints = new StringBuilder("");
        for (int i = 0; i < originalQuestions.size(); i++) {
            final int userSelectedAnswer = userSubmission.get(i).getSelectedAnswer();
            final boolean isCorrect = userSelectedAnswer >= 0 &&
                    originalQuestions.get(i).getAnswers().get(userSelectedAnswer).getCorrect();

            earnedPoints.append(isCorrect ? '1' : '0');

            if (i + 1 < originalQuestions.size()) {
                earnedPoints.append(',');
            }
        }

        return earnedPoints.toString();
    }

    private CodeTask findCodeTaskToExecute(long userId, CodeSubmitForm csf) {
        final Optional<Tournament> tournament = Optional.ofNullable(
                tournamentRepository.findActiveTournamentInWhichUserParticipatesById(csf.getTournamentId(), userId));

        if (tournament.isPresent()) {
            return tournament.get().getCodeTasks().stream()
                    .filter(task -> task.getId().equals(csf.getTaskId()))
                    .findFirst()
                    .orElse(null);
        }

        return null;
    }

    private QuizTask findQuizTaskToValidate(long userId, QuizSubmission qs) {
        final Optional<Tournament> tournament = Optional.ofNullable(
                tournamentRepository.findActiveTournamentInWhichUserParticipatesById(qs.getTournamentId(), userId));

        if (tournament.isPresent()) {
            return tournament.get().getQuizTasks().stream()
                    .filter(task -> task.getId().equals(qs.getTaskId()))
                    .findFirst()
                    .orElse(null);
        }

        return null;
    }

    private static class TaskResultDtoBuilder {
        private static final Pattern EARNED_POINTS_PATTERN =
                Pattern.compile("EARNED POINTS: ([^\n]+)");

        private static final Pattern EXECUTION_TIME_PATTERN =
                Pattern.compile("EXECUTION TIME: (\\d+)");

        private static CodeTaskResultDto fromCodeTaskResult(long userId, CodeTask codeTask, CodeSubmitForm csf, ExecutionResult executionResult) {
            final CodeTaskResultDto codeTaskResultDto = new CodeTaskResultDto();

            codeTaskResultDto.setUserId(userId);
            codeTaskResultDto.setTournamentId(csf.getTournamentId());
            codeTaskResultDto.setTaskId(csf.getTaskId());
            codeTaskResultDto.setLanguage(csf.getLanguage());
            codeTaskResultDto.setResultCode(csf.getProgram());

            final Matcher pointsMatcher = EARNED_POINTS_PATTERN.matcher(executionResult.getOutput());
            final Matcher timeMatcher = EXECUTION_TIME_PATTERN.matcher(executionResult.getOutput());

            codeTaskResultDto.setEarnedPoints(pointsMatcher.find() ? pointsMatcher.group(1) :
                    StringUtils.repeat("0", ",", codeTask.getCodeTaskTestSets().size()));

            codeTaskResultDto.setExecutionTime(timeMatcher.find() ? Long.parseLong(timeMatcher.group(1)) : 0);

            return codeTaskResultDto;
        }
    }

}
