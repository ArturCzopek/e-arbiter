package pl.cyganki.tournament.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
public class Answer {

    public Answer() {
        this.id = ObjectId.get().toString();
    }

    @Id
    private String id;

    @NotNull(message = "Answer's 'content' cannot be empty")
    private String content;

    private boolean correct;
}