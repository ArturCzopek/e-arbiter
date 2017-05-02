package pl.cyganki.executor.code;

public interface CodeRunner {
    ExecutionResult execute(byte[] program, String ext, byte[] testData);
}
