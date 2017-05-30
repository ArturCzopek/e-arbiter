package pl.cyganki.executor.code.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
class SandboxService implements AutoCloseable {

    private static final String FINISH_MESSAGE = "FINISHED";

    private final DockerClient dockerClient;
    private final SandboxConfig sandboxConfig;

    SandboxService(@Value("${docker.uri}") String dockerUri,
                   SandboxConfig sandboxConfig) {
        dockerClient = DefaultDockerClient
                .builder().uri(dockerUri).build();
        this.sandboxConfig = sandboxConfig;
    }

    @Override
    public void close() throws Exception {
        if (dockerClient != null) {
            dockerClient.close();
        }
    }

    ContainerCreation createContainer(DockerBinding binding)
            throws DockerException, InterruptedException {

        ContainerConfig config = sandboxConfig.createConfig(binding.getValue());
        return dockerClient.createContainer(config);
    }

    void startContainer(String id) throws DockerException, InterruptedException {
        dockerClient.startContainer(id);
    }

    String getContainerLogs(String id, long timeout)
            throws DockerException, InterruptedException, TimeoutException {

        FutureTask<String> logsFetching = new FutureTask<>(() -> {
            String logs = "";

            while (!logs.contains(FINISH_MESSAGE)) {
                logs = dockerClient.logs(id, DockerClient.LogsParam.stdout(),
                        DockerClient.LogsParam.stderr()).readFully();
            }

            return logs;
        });

        new Thread(logsFetching).start();
        String logs = "";

        try {
            logs = logsFetching.get(timeout, TimeUnit.MILLISECONDS);
        } catch (ExecutionException ignored) {}

        return logs;
    }

    void stopAndDelContainer(String id) throws DockerException, InterruptedException {
        stopContainer(id);
        delContainer(id);
    }

    void stopContainer(String id) throws DockerException, InterruptedException {
        dockerClient.stopContainer(id, 5);
    }

    void delContainer(String id) throws DockerException, InterruptedException {
        dockerClient.removeContainer(id);
    }
}
