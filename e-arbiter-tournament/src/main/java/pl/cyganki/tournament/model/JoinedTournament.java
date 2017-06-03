package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document(collection = "joined_tournaments")
@Data
@Builder
public class JoinedTournament {
    @Id
    private long joinedTournamentId;

    @NotNull(message = "Joined tournament cannot be null")
    private Tournament tournament;

    private String solution;

    private List<String> parameters;

    private List<String> answers;
}
