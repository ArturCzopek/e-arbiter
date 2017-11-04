package pl.cyganki.tournament.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Answer {

    @NotNull(message = "Answer's 'content' cannot be empty")
    private String content;

    private boolean correct;

    @JsonProperty
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @JsonIgnore
    public boolean getCorrect() {
        return this.correct;
    }
}