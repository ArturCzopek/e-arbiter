package pl.cyganki.executor.docker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.executor.code.ExecutionResult;
import pl.cyganki.executor.code.docker.DockerCodeRunner;

@RestController
@RequestMapping("/api")
public class ExecutorController {

    private final DockerCodeRunner codeRunner;

    @Autowired
    public ExecutorController(DockerCodeRunner codeRunner) {
        this.codeRunner = codeRunner;
    }

    @GetMapping("/example")
    public ExecutionResult example() {
        return codeRunner.test();
    }

    @GetMapping("/hystrix")
    @HystrixCommand(fallbackMethod = "rescue")
    @ApiOperation(value = "Hystrix demo")
    public String hystrix() throws Exception {
        throw new Exception();
    }

    public String rescue() {
        return "its fucked up";
    }
}
