package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class QuestionTask extends Task{
    private long questionId;

    @NotNull(message = "QuestionTask 'content' cannot be null")
    private String content;

    @NotNull(message = "QuestionTask 'answers' cannot be null")
    private List<Answer> answers;

    @Builder
    private QuestionTask(long taskId, double maxPoints, long questionId, String content, List<Answer> answers){
        super(taskId, maxPoints);
        this.questionId = questionId;
        this.content = content;
        this.answers = answers;
    }
}