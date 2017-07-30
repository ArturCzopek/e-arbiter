package pl.cyganki.tournament.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class QuizTask extends Task {

    @NotNull(message = "QuizTask's 'name' cannot be empty")
    @Size(min = 3, max = 64, message = "QuizTask's 'name' must be of length between 3 and 64 characters")
    private String name;

    @Valid
    @NotNull(message = "QuizTask's question list cannot be empty")
    @Size(min = 1, message = "QuizTask must contains at least one question")
    private List<Question> questions;

    @Override
    public long getMaxPoints() {
        return questions.size();
    }
}
