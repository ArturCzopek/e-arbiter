package pl.cyganki.executor.code.docker;

import lombok.Getter;

@Getter
class DockerBinding {

    private final String value;

    DockerBinding(String value) {
        this.value = value;
    }

    String getHostDir() {
        return value.split(":")[0];
    }

    String getContainerDir() {
        return value.split(":")[1];
    }

    DockerBinding appendToHostDir(String path) {
        return new DockerBinding(getHostDir() + path + ":" + getContainerDir());
    }
}
