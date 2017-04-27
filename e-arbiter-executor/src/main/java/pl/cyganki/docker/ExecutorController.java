package pl.cyganki.docker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.modules.AuthModule;

@RestController
public class ExecutorController {

    @Autowired
    private AuthModule authModule;

    @GetMapping("/execute")
    public String executeCode() {
        return "Executed: " + authModule.login();
    }
}
