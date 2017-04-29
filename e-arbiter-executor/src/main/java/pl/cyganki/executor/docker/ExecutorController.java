package pl.cyganki.executor.docker;

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
}
