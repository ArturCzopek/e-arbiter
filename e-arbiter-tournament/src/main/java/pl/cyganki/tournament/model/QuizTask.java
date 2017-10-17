package pl.cyganki.tournament.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class QuizTask extends Task {

    @Valid
    @NotNull(message = "QuizTask's question list cannot be empty")
    @Size(min = 1, message = "QuizTask must contains at least one question")
    private List<Question> questions;

    @NotNull(message = "Quiz task's attempts cannot be null")
    @Min(value = 1, message = "QuizTask must allows user for at least one attempt")
    private int maxAttempts;

    @Override
    public int getMaxPoints() {
        return questions.size();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
}
