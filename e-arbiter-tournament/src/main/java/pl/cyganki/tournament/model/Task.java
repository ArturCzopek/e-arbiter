package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "TASKS")
@Data
@Builder
public class Task {
    @Id
    private long taskId;

    @DBRef
    private List<CodeTask> codeTasks = new ArrayList<>();

    @DBRef
    private List<QuestionTask> questionTasks = new ArrayList<>();
}
