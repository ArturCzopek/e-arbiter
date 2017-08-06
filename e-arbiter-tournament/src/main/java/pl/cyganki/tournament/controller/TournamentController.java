package pl.cyganki.tournament.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.model.dto.TournamentPreview;
import pl.cyganki.tournament.repository.TournamentRepository;
import pl.cyganki.tournament.service.TournamentPreviewsFetcher;
import pl.cyganki.utils.security.dto.User;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TournamentController {

    private TournamentRepository tournamentRepository;
    private TournamentPreviewsFetcher tournamentPreviewsFetcher;

    @Autowired
    public TournamentController(TournamentRepository tournamentRepository, TournamentPreviewsFetcher tournamentPreviewsFetcher) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentPreviewsFetcher = tournamentPreviewsFetcher;
    }

    @PostMapping("/add")
    @ApiOperation("Endpoint for adding a new tournament. If is ok, then returns added tournament, else returns 4xx or 5xx code with error description")
    public Tournament addTournament(@RequestBody @Valid Tournament tournament) {
        // TODO create service with adding user id etc
        return tournamentRepository.save(tournament);
    }

    @GetMapping("/all/active")
    @ApiOperation("Returns a page with active tournaments' details in which logged in user participates")
    public Page<TournamentPreview> getActiveTournamentsInWhichUserParticipates(User user, Pageable pageable, @RequestParam(value = "query", required = false) String query) {
        return tournamentPreviewsFetcher.getActiveTournamentsInWhichUserParticipates(user.getId(), pageable, query);
    }

    @GetMapping("/all/finished")
    @ApiOperation("Returns a page with finished tournaments' details in which logged in user participates")
    public Page<TournamentPreview> getFinishedTournamentsInWhichUserParticipates(User user, Pageable pageable, @RequestParam(value = "query", required = false) String query) {
        return tournamentPreviewsFetcher.getFinishedTournamentsInWhichUserParticipates(user.getId(), pageable, query);
    }
}
