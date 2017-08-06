package pl.cyganki.tournament.model;

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
}