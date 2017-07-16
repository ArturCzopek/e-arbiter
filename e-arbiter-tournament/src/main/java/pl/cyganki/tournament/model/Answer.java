package pl.cyganki.tournament.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Answer {

    @NotNull(message = "Answer's 'content' cannot be empty")
    @Size(min = 3, max = 64, message = "Answer's 'content' must be of length between 3 and 64 characters")
    private String content;

    private boolean correct;
}