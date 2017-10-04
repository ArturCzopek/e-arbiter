package pl.cyganki.executor.docker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.executor.code.docker.DockerCodeRunner;

@RestController
@RequestMapping("/api")
public class ExecutorController {

    private final DockerCodeRunner codeRunner;

    @Autowired
    public ExecutorController(DockerCodeRunner codeRunner) {
        this.codeRunner = codeRunner;
    }
}
