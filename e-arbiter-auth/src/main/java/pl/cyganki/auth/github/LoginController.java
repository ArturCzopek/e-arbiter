package pl.cyganki.auth.github;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "test";
    }

    @GetMapping("/failLogin")
    @HystrixCommand(fallbackMethod = "login")
    public String failLogin() {
        throw new UnsupportedOperationException();
    }
}
