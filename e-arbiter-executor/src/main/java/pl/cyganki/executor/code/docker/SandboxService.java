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

    ContainerCreation createContainer(DockerBinding binding) {
        ContainerConfig config = sandboxConfig.createConfig(binding.getValue());

        ContainerCreation creation = null;

        try {
            creation = dockerClient.createContainer(config);
        } catch (DockerException | InterruptedException e) {
            e.printStackTrace();
        }

        return creation;
    }

    void startContainer(String id) throws DockerException, InterruptedException {
        dockerClient.startContainer(id);
    }

    String getContainerLogs(String id, int timeout)
            throws DockerException, InterruptedException, TimeoutException {

        FutureTask<String> logsFetching = new FutureTask<>(() -> {
            String logs = "";

            while (!logs.contains("FINISHED")) {
                logs = dockerClient.logs(id, DockerClient.LogsParam.stdout(),
                        DockerClient.LogsParam.stderr()).readFully();
            }

            return logs;
        });

        new Thread(logsFetching).start();
        String logs = "";

        try {
            logs = logsFetching.get(timeout, TimeUnit.SECONDS);
        } catch (ExecutionException ignored) {}

        return logs;
    }

    void stopContainer(String id) throws DockerException, InterruptedException {
        dockerClient.stopContainer(id, 5);
    }

    void delContainer(String id) throws DockerException, InterruptedException {
        dockerClient.removeContainer(id);
    }
}
