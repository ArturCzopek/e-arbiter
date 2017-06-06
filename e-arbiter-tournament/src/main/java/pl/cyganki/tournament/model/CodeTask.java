package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CodeTask extends Task implements Serializable{

    private static final long serialVersionUID = 8587016431191862140L;

    public enum Language {
        JAVA,
        PYTHON,
        C11,
        CPP
    }

    private long codeTaskId;

    @NotNull(message = "CodeTask 'test sets' cannot be null")
    private List<TestSet> testSets;

    // TODO: 6/5/17 move it to utils lib because we don't want to duplicate this language 
    @NotNull(message = "CodeTask 'language' cannot be null")
    private List<Language> languages;

    private double timeoutInMs;

    public CodeTask() {
        super(0, 0);
    }

    @Builder
    private CodeTask(long taskId, double maxPoints, long codeTaskId, List<TestSet> testSets, List<Language> languages, double timeoutInMs) {
        super(taskId, maxPoints);
        this.codeTaskId = codeTaskId;
        this.testSets = testSets;
        this.languages = languages;
        this.timeoutInMs = timeoutInMs;
    }
}