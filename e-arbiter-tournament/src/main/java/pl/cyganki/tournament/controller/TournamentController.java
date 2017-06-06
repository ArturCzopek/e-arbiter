package pl.cyganki.tournament.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.repository.TournamentRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TournamentController {

    private TournamentRepository tournamentRepository;

    @Autowired
    public TournamentController(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @GetMapping("/test")
    public List<Tournament> test() {
        return this.tournamentRepository.findAll();
    }
}
