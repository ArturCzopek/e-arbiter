package pl.cyganki.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CodeTask.class, name = "CodeTask"),
        @JsonSubTypes.Type(value = QuizTask.class, name = "QuizTask")
})
public abstract class Task {

    @NotNull(message = "Task's 'name' cannot be empty")
    @Size(min = 3, max = 64, message = "Task's 'name' must be of length between 3 and 64 characters")
    private String name;

    @NotNull
    private String description;

    abstract public long getMaxPoints();


}