package pl.cyganki.tournament.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document(collection = "joined_tournaments")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JoinedTournament {
    @Id
    private long joinedTournamentId;

    @NotNull(message = "Joined tournament cannot be null")
    private Tournament tournament;

    private String solution;

    private List<String> parameters;

    private List<String> answers;
}
