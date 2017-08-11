package pl.cyganki.results.controller.inner;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for prove of concepts
 */
@RestController
@RequestMapping("/poc")
@Profile("dev")
public class PocController {

    @GetMapping("/hystrix")
    @HystrixCommand(fallbackMethod = "rescue")
    public String hystrixResults() throws Exception {

        if (Math.random() < 0.5) {
            throw new Exception();
        } else {
            return "ok";
        }
    }

    public String rescue() {
        return "its fucked up from tournament results";
    }
}
