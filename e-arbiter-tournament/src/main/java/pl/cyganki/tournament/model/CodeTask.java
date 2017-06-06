package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CodeTask extends Task{

    public enum Language{
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
    private List<Language> language;

    private double timeoutInMs;

    @Builder
    private CodeTask(long taskId, double maxPoints, long codeTaskId, List<TestSet> testSets, List<Language> language, double timeoutInMs){
        super(taskId, maxPoints);
        this.codeTaskId = codeTaskId;
        this.testSets = testSets;
        this.language = language;
        this.timeoutInMs = timeoutInMs;
    }
}