package pl.cyganki.executor.docker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.cyganki.utils.modules.AuthModuleInterface;
import pl.cyganki.utils.security.User;
import pl.cyganki.executor.code.CodeRunner;
import pl.cyganki.executor.code.ExecutionResult;
import pl.cyganki.executor.code.docker.DockerCodeRunner;

@RestController
@RequestMapping("/api")
public class ExecutorController {

    private final AuthModuleInterface authModule;
    private final DockerCodeRunner codeRunner;

    @Autowired
    public ExecutorController(AuthModuleInterface authModule,
                              DockerCodeRunner codeRunner) {
        this.authModule = authModule;
        this.codeRunner = codeRunner;
    }

    @GetMapping("/execute")
    @ApiOperation(value = "Executes code")
    public User executeCode(@RequestHeader("oauth_token") String token) {
        return authModule.getUser(token);
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
