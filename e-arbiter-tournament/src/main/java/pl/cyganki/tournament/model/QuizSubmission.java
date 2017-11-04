package pl.cyganki.tournament.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class QuizSubmission {

    @NotNull
    private String tournamentId;

    @NotNull
    private String taskId;

    @NotNull
    List<Question> questions;
}
