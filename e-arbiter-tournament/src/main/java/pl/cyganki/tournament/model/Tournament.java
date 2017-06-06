package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "TOURNAMENTS")
@Data
@Builder
public class Tournament implements Serializable {

    private static final long serialVersionUID = -5370765864497317104L;

    @Id
    private long tournamentId;

    @NotNull(message = "Tournament 'ownerId' cannot be null")
    private long ownerId;

    @NotNull(message = "Tournament 'name' cannot be null")
    private String name;

    @CreatedDate
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "Tournament 'is public' cannot be null")
    private boolean publicFlag;

    private List<Long> joinedUsersId;

    @DBRef
    @NotNull(message = "Tournament 'tasks' cannot be null")
    private List<Task> tasks;

    @NotNull(message = "Tournament 'max points' cannot be null")
    private double maxPoints;
}