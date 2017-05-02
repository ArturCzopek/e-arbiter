package pl.cyganki.executor.code;

import lombok.Getter;

@Getter
public class ExecutionResult {
    private final Status status;
    private final String output;

    public ExecutionResult(Status status, String output) {
        this.status = status;
        this.output = output;
    }

    public enum Status {
        SUCCESS, FAILURE
    }
}
