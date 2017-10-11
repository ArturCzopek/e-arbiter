package pl.cyganki.tournament.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.model.dto.TournamentUserActionRequest;
import pl.cyganki.tournament.model.dto.TournamentDetails;
import pl.cyganki.tournament.model.dto.TournamentPreview;
import pl.cyganki.tournament.service.TournamentDetailsService;
import pl.cyganki.tournament.service.TournamentManagementService;
import pl.cyganki.tournament.service.TournamentPreviewsFetcher;
import pl.cyganki.tournament.service.TournamentUserActionService;
import pl.cyganki.utils.security.dto.User;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TournamentController {

    private TournamentPreviewsFetcher tournamentPreviewsFetcher;
    private TournamentManagementService tournamentManagementService;
    private TournamentDetailsService tournamentDetailsService;
    private TournamentUserActionService tournamentUserActionService;

    @Autowired
    public TournamentController(TournamentPreviewsFetcher tournamentPreviewsFetcher,
                                TournamentManagementService tournamentManagementService,
                                TournamentDetailsService tournamentDetailsService,
                                TournamentUserActionService tournamentUserActionService) {
        this.tournamentPreviewsFetcher = tournamentPreviewsFetcher;
        this.tournamentManagementService = tournamentManagementService;
        this.tournamentDetailsService = tournamentDetailsService;
        this.tournamentUserActionService = tournamentUserActionService;
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

    @GetMapping("/all/newest")
    @ApiOperation("Returns a page with the newest tournaments in which user does not participate")
    public Page<TournamentPreview> getActiveNewestTournamentsInWhichUserDoesNotParticipate(User user, Pageable pageable) {
        return tournamentPreviewsFetcher.getActiveNewestTournamentsInWhichUserDoesNotParticipate(user.getId(), pageable);
    }

    @GetMapping("/all/popular")
    @ApiOperation("Returns a page with the most popular tournaments in which user does not participate")
    public Page<TournamentPreview> getActiveMostPopularTournamentsInWhichUserDoesNotParticipate(User user, Pageable pageable) {
        return tournamentPreviewsFetcher.getActiveMostPopularTournamentsInWhichUserDoesNotParticipate(user.getId(), pageable);
    }

    @GetMapping("/all/ending")
    @ApiOperation("Returns a page with almost ended tournaments in which user does not participate")
    public Page<TournamentPreview> getActiveAlmostEndedTournamentsInWhichUserDoesNotParticipate(User user, Pageable pageable) {
        return tournamentPreviewsFetcher.getActiveAlmostEndedTournamentsInWhichUserDoesNotParticipate(user.getId(), pageable);
    }

    @GetMapping("/management/draft")
    @ApiOperation("Returns a page with draft tournaments which were created by user")
    public Page<TournamentPreview> getDraftTournamentsCreatedByUser(User user, Pageable pageable, @RequestParam(value = "query", required = false) String query) {
        return tournamentPreviewsFetcher.getDraftTournamentsCreatedByUser(user.getId(), pageable, query);
    }

    @GetMapping("/management/active")
    @ApiOperation("Returns a page with active tournaments which were created by user")
    public Page<TournamentPreview> getActiveTournamentsCreatedByUser(User user, Pageable pageable, @RequestParam(value = "query", required = false) String query) {
        return tournamentPreviewsFetcher.getActiveTournamentsCreatedByUser(user.getId(), pageable, query);
    }

    @GetMapping("/management/finished")
    @ApiOperation("Returns a page with finished tournaments which were created by user")
    public Page<TournamentPreview> getFinishedTournamentsCreatedByUser(User user, Pageable pageable, @RequestParam(value = "query", required = false) String query) {
        return tournamentPreviewsFetcher.getFinishedTournamentsCreatedByUser(user.getId(), pageable, query);
    }

    @GetMapping("/user-details/{id}")
    @ApiOperation("Returns specific information about tournament with passed id. Amount of information is depending on user access to that tournament")
    public TournamentDetails getTournamentDetails(User user, @PathVariable("id") String tournamentId) {
        return tournamentDetailsService.getTournamentDetailsForUser(user.getId(), tournamentId);
    }

    @PostMapping("/save")
    @ApiOperation("Endpoint for adding a new tournament. If is ok, then returns added tournament, else returns 4xx or 5xx code with error description")
    public Tournament saveTournament(User user, @RequestBody @Valid Tournament tournament) {
        return tournamentManagementService.saveTournament(user.getId(), tournament);
    }

    @PostMapping("/user-action/join")
    @ApiOperation("Endpoint for joining to an existing and active tournament.")
    public ResponseEntity<String> joinToTournament(User user, @RequestBody TournamentUserActionRequest tournamentUserActionRequest) {
        tournamentUserActionService.joinToTournament(user.getId(), tournamentUserActionRequest);
        return ResponseEntity.ok("User has joined to tournament");
    }

    @PostMapping("/user-action/leave")
    @ApiOperation("Endpoint for leaving from an existing and active tournament.")
    public ResponseEntity<String> leaveTournament(User user, @RequestBody TournamentUserActionRequest tournamentUserActionRequest) {
        tournamentUserActionService.leaveTournament(user.getId(), tournamentUserActionRequest);
        return ResponseEntity.ok("User has left tournament");
    }
}
