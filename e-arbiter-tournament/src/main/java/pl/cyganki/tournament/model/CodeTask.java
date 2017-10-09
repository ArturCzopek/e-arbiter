package pl.cyganki.tournament.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cyganki.utils.model.Language;

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
    public int getMaxPoints() {
        return codeTaskTestSets.size();
    }

    public byte[] getCodeTaskData() {
        StringBuilder sb = new StringBuilder();

        for (CodeTaskTestSet testSet: getCodeTaskTestSets()) {
            sb.append(String.join(" ", testSet.getParameters()));
            sb.append(" ");
            sb.append(testSet.getExpectedResult());
            sb.append("\n");
        }

        return sb.toString().getBytes();
    }
}