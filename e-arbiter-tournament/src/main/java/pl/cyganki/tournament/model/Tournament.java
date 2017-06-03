package pl.cyganki.tournament.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "tournaments")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tournament {
    @Id
    private long tournamentId;

    @DBRef
    @NotNull(message = "Tournament 'owner' cannot be null")
    private User owner;

    @NotNull(message = "Tournament 'name' cannot be null")
    private String name;

    @NotNull(message = "Tournament 'start time' cannot be null")
    private LocalDate startDate;

    @NotNull(message = "Tournament 'end time' cannot be null")
    private LocalDate endDate;

    @NotNull(message = "Tournament 'is public' cannot be null")
    private boolean isPublic;

    @DBRef
    private List<User> sharedUsers;

    @NotNull(message = "Tournament 'is code' cannot be null")
    private boolean isCode;

    //here tournament code properties
    private String language;

    private int timeout;

    private String solution;

    private List<String> parameters;

    @NotNull(message = "Tournament 'is test' cannot be null")
    private boolean isTest;

    //and here tournament test properties
    private List<Question> questions;

    private int maxPoints;
}
