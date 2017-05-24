package pl.cyganki.executor.docker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.cyganki.executor.model.User;
import pl.cyganki.executor.modules.AuthModuleInterface;

@RestController
@RequestMapping("/api")
public class ExecutorController {

    @Autowired
    private AuthModuleInterface authModule;

    @GetMapping("/execute")
    @ApiOperation(value = "Executes code")
    public User executeCode(@RequestHeader("oauth_token") String token) {
        User user =  authModule.getUser(token);
        return user;
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
