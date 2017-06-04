package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "CODE_TASKS")
@Data
@Builder
public class CodeTask {

    @Builder
    public static class TestSet {
        private String result;
        private List<String> parameters;

        public TestSet(String result, List<String> parameters){
            this.result=result;
            this.parameters=parameters;
        }
    }

    public enum Language{
        JAVA,
        PYTHON,
        C11,
        CPP
    }

    @Id
    private long codeTaskId;

    @NotNull(message = "CodeTask 'test sets' cannot be null")
    private List<TestSet> testSets = new ArrayList<>();

    @NotNull(message = "CodeTask 'language' cannot be null")
    private Language language;

    private int timeout;

    @NotNull(message = "CodeTask 'value' cannot be null")
    private double pointsForCodeTask;
}
