package pl.cyganki.tournament.controller

import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.cyganki.tournament.model.Tournament
import pl.cyganki.tournament.model.dto.TournamentPreview
import pl.cyganki.tournament.model.request.RemoveUserRequest
import pl.cyganki.tournament.service.TournamentManagementService
import pl.cyganki.tournament.service.TournamentPreviewsFetcher
import pl.cyganki.utils.security.dto.User
import javax.validation.Valid

@RestController
@RequestMapping("/api/management")
class TournamentManagementController(
        private val tournamentPreviewsFetcher: TournamentPreviewsFetcher,
        private val tournamentManagementService: TournamentManagementService
) {

    @GetMapping("/draft")
    @ApiOperation("Returns a page with draft tournaments which were created by user")
    fun getDraftTournamentsCreatedByUser(user: User, pageable: Pageable, @RequestParam(value = "query", required = false) query: String): Page<TournamentPreview> {
        return tournamentPreviewsFetcher.getDraftTournamentsCreatedByUser(user.id, pageable, query)
    }

    @GetMapping("/active")
    @ApiOperation("Returns a page with active tournaments which were created by user")
    fun getActiveTournamentsCreatedByUser(user: User, pageable: Pageable, @RequestParam(value = "query", required = false) query: String): Page<TournamentPreview> {
        return tournamentPreviewsFetcher.getActiveTournamentsCreatedByUser(user.id, pageable, query)
    }

    @GetMapping("/finished")
    @ApiOperation("Returns a page with finished tournaments which were created by user")
    fun getFinishedTournamentsCreatedByUser(user: User, pageable: Pageable, @RequestParam(value = "query", required = false) query: String): Page<TournamentPreview> {
        return tournamentPreviewsFetcher.getFinishedTournamentsCreatedByUser(user.id, pageable, query)
    }

    @PostMapping("/save")
    @ApiOperation("Endpoint for adding a new tournament. If is ok, then returns added tournament, else returns 4xx or 5xx code with error description")
    fun saveTournament(user: User, @RequestBody @Valid tournament: Tournament) = tournamentManagementService.saveTournament(user.id, tournament)


    @GetMapping("/{id}")
    @ApiOperation("Endpoint for fetching Tournament by id and user id.")
    fun getById(user: User, @PathVariable("id") id: String): Tournament {
        return tournamentManagementService.findTournamentByIdAndOwnerId(id, user.id)
    }

    @PutMapping("/{id}/activate")
    @ApiOperation("Endpoint for activating a tournament with given id.")
    fun activateTournament(user: User, @PathVariable("id") tournamentId: String): ResponseEntity<String> {
        tournamentManagementService.activateTournament(user.id, tournamentId)
        return ResponseEntity.ok("Successfully changed tournaments status to ACTIVE.")
    }

    @PutMapping("/{id}/delete")
    @ApiOperation("Endpoint for deleting a tournament with given id.")
    fun deleteTournament(user: User, @PathVariable("id") tournamentId: String): ResponseEntity<String> {
        tournamentManagementService.deleteTournament(user.id, tournamentId)
        return ResponseEntity.ok("Successfully deleted tournament.")
    }

    @PutMapping("/remove-user")
    @ApiOperation("Endpoint for removing user from the tournament. If user has been removed, tournament is returned, else returns 4xx or 5xx code with error description")
    fun removeUserFromTournament(@RequestBody removeUserRequest: RemoveUserRequest) = with(removeUserRequest) {
        tournamentManagementService.removeUserFromTournament(requestAuthorId, userToBeRemovedId, tournamentId)
    }
}
