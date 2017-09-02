package pl.cyganki.tournament.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class Question {

    @NotNull(message = "Question's 'content' cannot be empty")
    @Size(min = 3, max = 200, message = "Question's 'content' must be of length between 3 and 64 characters")
    private String content;

    @Valid
    @NotNull(message = "Question's answer list cannot be empty")
    @Size(min = 2, message = "Question must contain at least two different answers")
    private List<Answer> answers;
}