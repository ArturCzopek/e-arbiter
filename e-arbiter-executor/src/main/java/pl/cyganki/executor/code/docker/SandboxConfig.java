package pl.cyganki.executor.code.docker;

import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.HostConfig;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class SandboxConfig {

    // container settings: image, startup command
    private final String dockerImg;
    private final String[] dockerCmd;

    SandboxConfig(@Value("${docker.img}") String dockerImg,
                  @Value("${docker.cmd}") String[] dockerCmd) {
        this.dockerImg = dockerImg;
        this.dockerCmd = dockerCmd;
    }

    ContainerConfig createConfig(String binding) {
        return ContainerConfig.builder()
                .image(dockerImg)
                .cmd(dockerCmd)
                .hostConfig(createHostConfigWithBinding(binding))
                .attachStdout(true)
                .attachStderr(true)
                .build();
    }

    private HostConfig createHostConfigWithBinding(String binding) {
        return HostConfig.builder()
                .appendBinds(binding)
                .build();
    }
}
