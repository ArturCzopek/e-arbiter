package pl.cyganki.tournament.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.repository.TournamentRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TournamentController {

    private TournamentRepository tournamentRepository;

    @Autowired
    public TournamentController(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @PostMapping("/add")
    @ApiOperation("Endpoint for adding a new tournament. If is ok, then returns added tournament, else returns 4xx or 5xx code with error description")
    public Tournament addTournament(@RequestBody @Valid Tournament tournament) {
        // TODO create service with adding user id etc
        return tournamentRepository.save(tournament);
    }
}
