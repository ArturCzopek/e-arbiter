package pl.cyganki.executor.docker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.executor.modules.AuthModuleInterface;

@RestController
public class ExecutorController {

    @Autowired
    private AuthModuleInterface authModule;

    @GetMapping("/api/execute")
    @ApiOperation(value = "Executes code")
    public String executeCode() {
        return "Executed: " + authModule.login();
    }

    @GetMapping("/api/executeFail")
    @ApiOperation(value = "Executes code with failed login")
    public String executeFailCode() {
        return "Failed: " + authModule.failLogin(); // there should be login too
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
