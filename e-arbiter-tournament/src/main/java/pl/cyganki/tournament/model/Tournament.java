package pl.cyganki.tournament.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.cyganki.tournament.exception.IllegalTournamentStatusException;
import pl.cyganki.tournament.exception.WrongUserParticipateStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Document(collection = "TOURNAMENTS")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

    @Id
    private String id;

    private Long ownerId;

    @NotNull(message = "Tournament's 'name' cannot be empty")
    @Size(min = 3, max = 64, message = "Tournament's 'name' must be of length between 3 and 64 characters")
    private String name;

    private String description;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime endDate;

    private boolean publicFlag;

    private List<Long> joinedUsersIds = new LinkedList<>();

    private List<Long> blockedUsersIds = new LinkedList<>();

    private boolean resultsVisibleForJoinedUsers;

    private String password;

    protected TournamentStatus status;

    @Valid
    @NotNull(message = "Tournament's task list cannot be empty")
    @Size(min = 1, message = "Tournament must contain at least one task")
    private List<Task> tasks = new LinkedList<>();

    private static class AllowedStatuses {
        final static List<TournamentStatus> DRAFT = Arrays.asList(TournamentStatus.DRAFT);
        final static List<TournamentStatus> ACTIVE = Arrays.asList(TournamentStatus.ACTIVE);
        final static List<TournamentStatus> DRAFT_ACTIVE = Arrays.asList(TournamentStatus.DRAFT, TournamentStatus.ACTIVE);
    }

    public int getMaxPoints() {
        return tasks.stream().mapToInt(Task::getMaxPoints).sum();
    }

    public String getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public boolean isPublicFlag() {
        return publicFlag;
    }

    public List<Long> getJoinedUsersIds() {
        return joinedUsersIds;
    }

    public List<Long> getBlockedUsersIds() {
        return blockedUsersIds;
    }

    public boolean isResultsVisibleForJoinedUsers() {
        return resultsVisibleForJoinedUsers;
    }

    public String getPassword() {
        return password;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOwnerId(Long ownerId) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.ownerId = ownerId;
    }

    public void setName(String name) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.name = name;
    }

    public void setDescription(String description) {
        checkTournamentStatus(AllowedStatuses.DRAFT_ACTIVE);
        this.description = description;
    }

    public void setEndDate(LocalDateTime endDate) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.endDate = endDate;
    }

    public void extendDeadline(Duration duration) {
        checkTournamentStatus(AllowedStatuses.ACTIVE);
        this.endDate = this.endDate.plus(duration);
    }

    public void setPublicFlag(boolean publicFlag) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.publicFlag = publicFlag;
    }

    public void addUser(Long userId) {
        checkTournamentStatus(AllowedStatuses.ACTIVE);
        this.joinedUsersIds.add(userId);
    }

    public void blockUser(Long userId) {
        checkTournamentStatus(AllowedStatuses.ACTIVE);

        if (!this.joinedUsersIds.contains(userId)) {
            throw new WrongUserParticipateStatusException(userId, this.id);
        }

        this.joinedUsersIds.remove(userId);
        this.blockedUsersIds.add(userId);
    }

    public void unblockUser(Long userId) {
        checkTournamentStatus(AllowedStatuses.ACTIVE);

        if (!this.blockedUsersIds.contains(userId)) {
            throw new WrongUserParticipateStatusException(userId, this.id);
        }

        this.blockedUsersIds.remove(userId);
        this.joinedUsersIds.add(userId);
    }

    public void setResultsVisibleForJoinedUsers(boolean resultsVisibleForJoinedUsers) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.resultsVisibleForJoinedUsers = resultsVisibleForJoinedUsers;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }

    public void activate() {
        checkTournamentStatus(AllowedStatuses.DRAFT);

        if (this.endDate.isBefore(LocalDateTime.now())) {
            throw new DateTimeException("Tournament cannot end earlier than now!");
        }

        this.status = TournamentStatus.ACTIVE;
        this.startDate = LocalDateTime.now();
        this.joinedUsersIds = new ArrayList<>();
        this.blockedUsersIds = new ArrayList<>();
    }

    public void finish() {
        checkTournamentStatus(AllowedStatuses.ACTIVE);
        this.status = TournamentStatus.FINISHED;
    }

    public void setTasks(List<Task> tasks) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.tasks.add(task);
    }

    public Task getTask(String taskId) {
        return this.tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElse(null);
    }

    public List<CodeTask> getCodeTasks() {
        return tasks.stream()
                .filter(CodeTask.class::isInstance)
                .map(CodeTask.class::cast)
                .collect(Collectors.toList());
    }

    public List<QuizTask> getQuizTasks() {
        return tasks.stream()
                .filter(QuizTask.class::isInstance)
                .map(QuizTask.class::cast)
                .collect(Collectors.toList());
    }

    public void removeTask(String taskId) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.tasks.remove(
                this.tasks.stream()
                        .filter(task -> task.getId().equals(taskId))
                        .findFirst()
                        .orElse(null)
        );
    }

    public void updateTask(Task taskToUpdate) {
        checkTournamentStatus(AllowedStatuses.DRAFT);

        final int notFoundIndex = -1;

        int taskToUpdateIndex = IntStream
                .range(0, tasks.size())
                .filter(i -> this.tasks.get(i).getId().equals(taskToUpdate.getId())).findFirst()
                .orElse(notFoundIndex);

        if (taskToUpdateIndex != notFoundIndex) {
            this.tasks.set(taskToUpdateIndex, taskToUpdate);
        }
    }

    private void checkTournamentStatus(List<TournamentStatus> allowedStatuses) {
        if (!allowedStatuses.contains(this.status)) {
            throw new IllegalTournamentStatusException(this.status, allowedStatuses);
        }
    }
}