package pl.cyganki.auth.github;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class LoginController {

    @GetMapping("/login")
    @ApiOperation(value = "log in user", nickname = "login")
    public String login() {
        return "test";
    }

    @GetMapping("/failLogin")
    @ApiOperation(value = "log in user by hystrix method", nickname = "failLogin")
    @HystrixCommand(fallbackMethod = "login")
    public String failLogin() {
        throw new UnsupportedOperationException();
    }
}
