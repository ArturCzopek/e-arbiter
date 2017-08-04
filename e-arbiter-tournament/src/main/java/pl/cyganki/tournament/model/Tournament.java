package pl.cyganki.tournament.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.cyganki.tournament.exception.IllegalTournamentStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;


@Document(collection = "TOURNAMENTS")
@Data
@NoArgsConstructor
public class Tournament {

    @Id
    private String id;

    // todo: allows nulls there. Owner Id should be fetched from request object and set then
    @NotNull(message = "Tournament's 'ownerId' cannot be empty")
    @Setter(AccessLevel.NONE)
    private Long ownerId;

    @NotNull(message = "Tournament's 'name' cannot be empty")
    @Size(min = 3, max = 64, message = "Tournament's 'name' must be of length between 3 and 64 characters")
    @Setter(AccessLevel.NONE)
    private String name;

    @Setter(AccessLevel.NONE)
    private String description;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Setter(AccessLevel.NONE)
    private LocalDateTime startDate;

    @NotNull
    @Setter(AccessLevel.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    protected LocalDateTime endDate;

    @Setter(AccessLevel.NONE)
    private boolean publicFlag;

    @Setter(AccessLevel.NONE)
    private List<Long> joinedUsersIds = new LinkedList<>();

    @Setter(AccessLevel.NONE)
    private boolean resultsVisibleForJoinedUsers;

    @Setter(AccessLevel.NONE)
    private String password;

    @Setter(AccessLevel.NONE)
    protected TournamentStatus status = TournamentStatus.DRAFT;

    @Valid
    @NotNull(message = "Tournament's task list cannot be empty")
    @Size(min = 1, message = "Tournament must contain at least one task")
    @Setter(AccessLevel.NONE)
    private List<Task> tasks = new LinkedList<>();

    private static class AllowedStatuses {
        final static List<TournamentStatus> DRAFT = Arrays.asList(TournamentStatus.DRAFT);
        final static List<TournamentStatus> ACTIVE = Arrays.asList(TournamentStatus.ACTIVE);
        final static List<TournamentStatus> DRAFT_ACTIVE = Arrays.asList(TournamentStatus.DRAFT, TournamentStatus.ACTIVE);
    }

    public long getMaxPoints() {
        return tasks.stream().mapToLong(Task::getMaxPoints).sum();
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

    public void extendDeadline(Period period) {
        checkTournamentStatus(AllowedStatuses.ACTIVE);
        this.endDate = this.endDate.plus(period);
    }

    public void setPublicFlag(boolean publicFlag) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.publicFlag = publicFlag;
    }

    public void addUser(Long userId) {
        checkTournamentStatus(AllowedStatuses.ACTIVE);
        this.joinedUsersIds.add(userId);
    }

    public void removeUser(Long userId) {
        checkTournamentStatus(AllowedStatuses.ACTIVE);
        this.joinedUsersIds.remove(userId);
    }

    public void setResultsVisibleForJoinedUsers(boolean resultsVisibleForJoinedUsers) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.resultsVisibleForJoinedUsers = resultsVisibleForJoinedUsers;
    }

    public void setPassword(String password) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.password = password;
    }

    public void activate() {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.status = TournamentStatus.ACTIVE;
        this.startDate = LocalDateTime.now();
        this.joinedUsersIds = new ArrayList<>();
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
        return this.tasks.stream().filter(task -> task.getId() == taskId).findFirst().orElse(null);
    }

    public void removeTask(String taskId) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        this.tasks.removeIf(task -> task.getId() == taskId);
    }

    public void updateTask(Task task) {
        checkTournamentStatus(AllowedStatuses.DRAFT);
        IntStream.range(0, this.tasks.size())
                .filter(i -> this.tasks.get(i).getId() == task.getId())
                .forEach(i -> this.tasks.set(i, task));
    }

    private void checkTournamentStatus(List<TournamentStatus> allowedStatuses) {
        if (!allowedStatuses.contains(this.status)) {
            throw new IllegalTournamentStatusException(this.status, allowedStatuses);
        }
    }
}