package pl.cyganki.solution.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SolutionController {

    @GetMapping("/test")
    public String test() {
        return "Solution";
    }
}
