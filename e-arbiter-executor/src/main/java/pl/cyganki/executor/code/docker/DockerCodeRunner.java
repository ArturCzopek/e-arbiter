package pl.cyganki.executor.code.docker;

import com.google.common.io.Files;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.cyganki.executor.code.CodeRunner;
import pl.cyganki.executor.code.ExecutionResult;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static pl.cyganki.executor.code.ExecutionResult.Status;

@Component
public class DockerCodeRunner implements CodeRunner {

    private final SandboxService sandboxService;
    private final FilesystemUtils filesystemUtils;
    private final DockerBinding dockerBinding;

    @Autowired
    public DockerCodeRunner(SandboxService sandboxService, FilesystemUtils filesystemUtils,
                            @Value("${docker.binding}") String bindingValue) {
        this.sandboxService = sandboxService;
        this.filesystemUtils = filesystemUtils;
        this.dockerBinding = new DockerBinding(bindingValue);
    }

    @Override
    public ExecutionResult execute(byte[] program, String ext, byte[] testData) {
        DockerBinding binding = dockerBinding.appendToHostDir(getUniqueName());
        String hostDir = binding.getHostDir();

        filesystemUtils.saveFile(program, "program." + ext, hostDir);
        filesystemUtils.saveFile(testData, "test_data", hostDir);

        ContainerCreation creation = sandboxService.createContainer(binding);
        String id = creation.id();

        ExecutionResult result = null;

        try {
            sandboxService.startContainer(id);
            result = parseOutput(sandboxService.getContainerLogs(id));
            sandboxService.stopContainer(id, 20);
            sandboxService.delContainer(id);
        } catch (DockerException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            filesystemUtils.deleteDir(hostDir);
        }

        return result;
    }

    private String getUniqueName() {
        return UUID.randomUUID().toString();
    }

    private ExecutionResult parseOutput(String output) {
        Status status = output.contains("ALL PASSED") ? Status.SUCCESS : Status.FAILURE;
        return new ExecutionResult(status, output);
    }

    @PostConstruct
    void test() {
        File program = new File("/home/maciej/Projects/compilebox/solution/program.c");
        File testData = new File("/home/maciej/Projects/compilebox/solution/test_data");

        try {
            byte[] programBytes = Files.toByteArray(program);
            byte[] testDataBytes = Files.toByteArray(testData);
            ExecutionResult result = execute(programBytes, "c", testDataBytes);
            System.out.println(result.getOutput());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
