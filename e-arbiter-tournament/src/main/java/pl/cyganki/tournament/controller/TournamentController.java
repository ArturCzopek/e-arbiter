package pl.cyganki.tournament.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TournamentController {

    @GetMapping("/test")
    public String test() {
        return "Tournament";
    }
}