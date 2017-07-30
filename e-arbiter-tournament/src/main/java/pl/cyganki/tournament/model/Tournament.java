package pl.cyganki.tournament.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "TOURNAMENTS")
@Data
@NoArgsConstructor
public class Tournament {

    @Id
    private String id;

    @NotNull(message = "Tournament's 'ownerId' cannot be empty")
    private Long ownerId;

    @NotNull(message = "Tournament's 'name' cannot be empty")
    @Size(min = 3, max = 64, message = "Tournament's 'name' must be of length between 3 and 64 characters")
    private String name;

    private String description;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime startDate;

    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    private boolean publicFlag;

    private List<Long> joinedUsersId;

    private boolean resultsVisibleForJoinedUsers;

    private String password;

    @Valid
    @NotNull(message = "Tournament's task list cannot be empty")
    @Size(min = 1, message = "Tournament must contain at least one task")
    private List<Task> tasks;

    public long getMaxPoints() {
        return tasks.stream().mapToLong(Task::getMaxPoints).sum();
    }
}