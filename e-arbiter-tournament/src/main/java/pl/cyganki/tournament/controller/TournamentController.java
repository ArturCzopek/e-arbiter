package pl.cyganki.tournament.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.repository.TournamentRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TournamentController {

    private TournamentRepository tournamentRepository;

    @Autowired
    public TournamentController(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @PostMapping("/add")
    public Tournament addTournament(@RequestBody @Valid Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @GetMapping("/test")
    public List<Tournament> test() {
        return this.tournamentRepository.findAll();
    }
}
