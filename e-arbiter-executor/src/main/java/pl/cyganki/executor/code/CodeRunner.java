package pl.cyganki.executor.code;


import pl.cyganki.utils.model.executor.ExecutionRequest;
import pl.cyganki.utils.model.executor.ExecutionResult;

public interface CodeRunner {
    ExecutionResult execute(ExecutionRequest executionRequest);
}
