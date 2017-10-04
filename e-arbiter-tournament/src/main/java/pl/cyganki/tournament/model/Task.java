package pl.cyganki.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CodeTask.class, name = "CodeTask"),
        @JsonSubTypes.Type(value = QuizTask.class, name = "QuizTask")
})
public abstract class Task {

    public Task() {
        this.id = new ObjectId().toString();
    }

    private String id;

    @NotNull(message = "Task's 'name' cannot be empty")
    @Size(min = 3, max = 64, message = "Task's 'name' must be of length between 3 and 64 characters")
    private String name;

    @NotNull
    private String description;

    public void setId(String id) {
        if (id == null) {
            this.id = new ObjectId().toString();
        } else {
            this.id = id;
        }
    }

    abstract public long getMaxPoints();
}