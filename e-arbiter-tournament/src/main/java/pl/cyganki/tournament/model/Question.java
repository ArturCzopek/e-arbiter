package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Document(collection = "questions")
@Data
@Builder
public class Question {
    @Id
    private long questionId;

    @NotNull(message = "Question 'value' cannot be null")
    private int value;

    @NotNull(message = "Question 'content' cannot be null")
    private String content;

    @NotNull(message = "Question 'answers' cannot be null")
    private Map<String, Boolean> answers;
}
