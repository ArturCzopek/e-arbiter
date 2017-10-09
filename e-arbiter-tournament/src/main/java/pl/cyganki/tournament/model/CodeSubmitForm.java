package pl.cyganki.tournament.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cyganki.utils.model.Language;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CodeSubmitForm {

    @NotNull(message = "Tournament's id cannot be empty")
    private String tournamentId;

    @NotNull(message = "Task's id cannot be empty")
    private String taskId;

    @NotNull(message = "Program cannot be empty")
    private String program;

    @NotNull(message = "Language cannot be empty")
    private Language language;
}
