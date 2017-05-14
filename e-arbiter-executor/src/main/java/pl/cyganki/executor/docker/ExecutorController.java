package pl.cyganki.executor.docker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.executor.modules.AuthModuleInterface;

@RestController
public class ExecutorController {

    @Autowired
    private AuthModuleInterface authModule;

    @GetMapping("/execute")
    public String executeCode() {
        return "Executed: " + authModule.login();
    }

    @GetMapping("/executeFail")
    public String executeFailCode() {
        return "Failed: " + authModule.failLogin(); // there should be login too
    }

    @GetMapping("/hystrix")
    @HystrixCommand(fallbackMethod = "rescue")
    public String hystrix() throws Exception {
        throw new Exception();
    }

    public String rescue() {
        return "its fucked up";
    }
}
