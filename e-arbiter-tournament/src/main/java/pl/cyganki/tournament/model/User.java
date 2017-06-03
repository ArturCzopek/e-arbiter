package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
public class User {
    @Id
    private long userId;

    @NotNull(message = "User 'name' cannot be null")
    private String name;

    @DBRef
    private List<JoinedTournament> joinedTournaments;
}
