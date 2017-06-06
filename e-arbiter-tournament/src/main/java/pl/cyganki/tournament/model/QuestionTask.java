package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionTask extends Task implements Serializable {
    
    private static final long serialVersionUID = 4513632127123060895L;
    private long questionId;

    @NotNull(message = "QuestionTask 'content' cannot be null")
    private String content;

    @NotNull(message = "QuestionTask 'answers' cannot be null")
    private List<Answer> answers;

    public QuestionTask() {
        super(0, 0);
    }

    @Builder
    private QuestionTask(long taskId, double maxPoints, long questionId, String content, List<Answer> answers) {
        super(taskId, maxPoints);
        this.questionId = questionId;
        this.content = content;
        this.answers = answers;
    }
}