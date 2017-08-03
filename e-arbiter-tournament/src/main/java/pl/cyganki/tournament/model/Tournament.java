package pl.cyganki.tournament.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDateTime endDate;

    @Setter(AccessLevel.NONE)
    private boolean publicFlag;

    @Setter(AccessLevel.NONE)
    private List<Long> joinedUsersId = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    private boolean resultsVisibleForJoinedUsers;

    @Setter(AccessLevel.NONE)
    private String password;

    @Setter(AccessLevel.NONE)
    private TournamentStatus status = TournamentStatus.DRAFT;

    @Valid
    @NotNull(message = "Tournament's task list cannot be empty")
    @Size(min = 1, message = "Tournament must contain at least one task")
    @Setter(AccessLevel.NONE)
    private List<Task> tasks;

    public long getMaxPoints() {
        return tasks.stream().mapToLong(Task::getMaxPoints).sum();
    }
}