package pl.cyganki.executor.code.docker;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    String getContainerLogs(String id) throws DockerException, InterruptedException {
        return dockerClient.logs(id, DockerClient.LogsParam.stdout(), DockerClient.LogsParam.stderr()).readFully();
    }

    void stopContainer(String id, int timeout) throws DockerException, InterruptedException {
        dockerClient.stopContainer(id, timeout);
    }
}
