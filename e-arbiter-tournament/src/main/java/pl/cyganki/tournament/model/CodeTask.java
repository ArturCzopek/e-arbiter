package pl.cyganki.tournament.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cyganki.utils.modules.tournament.model.Language;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class CodeTask extends Task {

    @Valid
    @NotNull(message = "CodeTask's list of test sets cannot be empty")
    @Size(min = 1, message = "CodeTask must contain at least one test set")
    private List<CodeTaskTestSet> codeTaskTestSets;

    @NotNull(message = "CodeTask's language list cannot be empty")
    @Size(min = 1, message = "CodeTask must support at least one language")
    private List<Language> languages;

    private long timeoutInMs;

    @Override
    public long getMaxPoints() {
        return codeTaskTestSets.size();
    }


}