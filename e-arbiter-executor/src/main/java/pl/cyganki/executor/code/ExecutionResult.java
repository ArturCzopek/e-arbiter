package pl.cyganki.executor.code;

import lombok.Getter;

@Getter
public class ExecutionResult {
    private final Status status;
    private final String output;
    private final long duration;

    public ExecutionResult(Status status, String output, long duriation) {
        this.status = status;
        this.output = output;
        this.duration = duriation;
    }

    public enum Status {
        SUCCESS, FAILURE
    }
}
