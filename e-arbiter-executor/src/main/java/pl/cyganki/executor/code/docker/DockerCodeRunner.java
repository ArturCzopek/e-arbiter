package pl.cyganki.executor.code.docker;

import com.google.common.io.Files;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerCreation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.cyganki.executor.code.CodeRunner;
import pl.cyganki.executor.code.ExecutionResult;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;

import static pl.cyganki.executor.code.ExecutionResult.Status;

@Service
@Slf4j
public class DockerCodeRunner implements CodeRunner {

    private static final Semaphore SEM =
            new Semaphore(Runtime.getRuntime().availableProcessors());

    private final SandboxService sandboxService;
    private final FileSystemUtils fileSystemUtils;
    private final DockerBinding dockerBinding;

    @Autowired
    public DockerCodeRunner(SandboxService sandboxService, FileSystemUtils fileSystemUtils,
                            @Value("${docker.binding}") String bindingValue) {
        this.sandboxService = sandboxService;
        this.fileSystemUtils = fileSystemUtils;
        this.dockerBinding = new DockerBinding(bindingValue);
    }

    @Override
    public ExecutionResult execute(byte[] program, String ext, byte[] testData) {
        DockerBinding binding = dockerBinding.appendToHostDir(getUniqueName());
        String hostDir = binding.getHostDir();

        fileSystemUtils.saveFile(program, "program." + ext, hostDir);
        fileSystemUtils.saveFile(testData, "test_data", hostDir);

        String containerId = null;
        ExecutionResult result;

        try {
            ContainerCreation creation = sandboxService.createContainer(binding);
            containerId = creation.id();

            log.info("Acquired semaphore, starting container...");
            SEM.acquire();

            try {
                sandboxService.startContainer(containerId);
                result = parseOutput(sandboxService.getContainerLogs(containerId, 5 * 1000));
            } finally {
                log.info("Released semaphore, stopping container...");
                SEM.release();
            }

        } catch (DockerException | InterruptedException e) {
            result = new ExecutionResult(Status.INTERNAL_ERROR, e.getMessage());
        } catch (TimeoutException e) {
            result = new ExecutionResult(Status.TIMEOUT, "");
        } finally {
            if (containerId != null) {
                try {
                    sandboxService.stopAndDelContainer(containerId);
                } catch (DockerException | InterruptedException ignored) {}
            }
            fileSystemUtils.deleteDir(hostDir);
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
}
