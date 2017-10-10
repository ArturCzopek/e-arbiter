package pl.cyganki.executor.code;

import pl.cyganki.utils.modules.executor.model.ExecutionResult;

public interface CodeRunner {
    ExecutionResult execute(byte[] program, String ext, byte[] testData);
}
