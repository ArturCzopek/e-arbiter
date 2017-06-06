package pl.cyganki.tournament.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TASKS")
@AllArgsConstructor
@Data
public abstract class Task {

    @Id
    protected long taskId;

    protected double maxPoints;
}