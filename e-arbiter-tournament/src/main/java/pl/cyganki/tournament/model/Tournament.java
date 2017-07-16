package pl.cyganki.tournament.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "TOURNAMENTS")
@Data
@NoArgsConstructor
public class Tournament implements Serializable {

    private static final long serialVersionUID = -5370765864497317104L;

    @Id
    private String id;

    public Tournament(Long ownerId, String name, LocalDate endDate,
                      boolean publicFlag, List<Task> tasks) {
        this.ownerId = ownerId;
        this.name = name;
        this.endDate = endDate;
        this.publicFlag = publicFlag;
        this.tasks = tasks;
    }

    @NotNull(message = "Tournament 'ownerId' cannot be null")
    private Long ownerId;

    @NotNull(message = "Tournament 'name' cannot be null")
    @Size(min = 3, max = 64, message = "Tournament 'name' must be of length between 3 and 64 characters")
    private String name;

    private String description;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    private boolean publicFlag;

    private List<Long> joinedUsersId;

    @DBRef
    @NotNull(message = "Tournament 'tasks' cannot be null")
    @Size(min = 1, message = "Tournament must contain at least one task")
    private List<Task> tasks;

    private long maxPoints;
}