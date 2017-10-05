package pl.cyganki.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode
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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    abstract public int getMaxPoints();
}