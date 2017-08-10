package pl.cyganki.tournament.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Question {

    public Question() {
        this.id = ObjectId.get().toString();
    }

    @Id
    private String id;

    @NotNull(message = "Question's 'content' cannot be empty")
    @Size(min = 3, max = 64, message = "Question's 'content' must be of length between 3 and 64 characters")
    private String content;

    @Valid
    @NotNull(message = "Question's answer list cannot be empty")
    @Size(min = 2, message = "Question must contain at least two different answers")
    private List<Answer> answers;
}