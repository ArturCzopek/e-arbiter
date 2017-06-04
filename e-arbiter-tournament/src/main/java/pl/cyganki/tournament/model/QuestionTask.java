package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "QUESTIONS_TASKS")
@Data
@Builder
public class QuestionTask {

    @Builder
    public static class Answer {
        private String content;
        private boolean value;

        public Answer(String content, boolean value) {
            this.content = content;
            this.value = value;
        }
    }

    @Id
    private long questionId;

    @NotNull(message = "QuestionTask 'value' cannot be null")
    private double pointForQuestionTask;

    @NotNull(message = "QuestionTask 'content' cannot be null")
    private String content;

    @NotNull(message = "QuestionTask 'answers' cannot be null")
    private List<Answer> answers= new ArrayList<>();
}
