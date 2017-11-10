package pl.cyganki.tournament.controller.api

import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.cyganki.tournament.model.Tournament
import pl.cyganki.tournament.service.TournamentManagementService
import pl.cyganki.tournament.service.TournamentPreviewsFetcher
import pl.cyganki.utils.security.dto.User
import java.time.Duration
import javax.validation.Valid

@RestController
@RequestMapping("/api/management")
class TournamentManagementController(
        private val tournamentPreviewsFetcher: TournamentPreviewsFetcher,
        private val tournamentManagementService: TournamentManagementService
) {

    @GetMapping("/draft")
    @ApiOperation("Returns a page with draft tournaments which were created by user")
    fun getDraftTournamentsCreatedByUser(user: User, pageable: Pageable, @RequestParam(value = "query", required = false) query: String) =
            tournamentPreviewsFetcher.getDraftTournamentsCreatedByUser(user.id, pageable, query)

    @GetMapping("/active")
    @ApiOperation("Returns a page with active tournaments which were created by user")
    fun getActiveTournamentsCreatedByUser(user: User, pageable: Pageable, @RequestParam(value = "query", required = false) query: String) =
            tournamentPreviewsFetcher.getActiveTournamentsCreatedByUser(user.id, pageable, query)

    @GetMapping("/finished")
    @ApiOperation("Returns a page with finished tournaments which were created by user")
    fun getFinishedTournamentsCreatedByUser(user: User, pageable: Pageable, @RequestParam(value = "query", required = false) query: String) =
            tournamentPreviewsFetcher.getFinishedTournamentsCreatedByUser(user.id, pageable, query)

    @PostMapping("/save")
    @ApiOperation("Endpoint for adding a new tournament. If is ok, then returns added tournament, else returns 4xx or 5xx code with error description")
    fun saveTournament(user: User, @RequestBody @Valid tournament: Tournament) =
            tournamentManagementService.saveTournament(user.id, tournament)

    @GetMapping("/{id}")
    @ApiOperation("Endpoint for fetching Tournament by id and user id.")
    fun getById(user: User, @PathVariable("id") id: String) =
            tournamentManagementService.findTournamentByIdAndOwnerId(id, user.id)

    @GetMapping("/{id}/enrolled-users")
    @ApiOperation("Endpoint for fetching a list of users enrolled in given tournament.")
    fun getEnrolledUsers(user: User, @PathVariable("id") tournamentId: String) =
            tournamentManagementService.getUsersEnrolledIntoTournament(user.id, tournamentId)

    @GetMapping("/{id}/blocked-users")
    @ApiOperation("Endpoint for fetching a list of users blocked in given tournament.")
    fun getBlockedUsers(user: User, @PathVariable("id") tournamentId: String) =
            tournamentManagementService.getUsersBlockedInTournament(user.id, tournamentId)

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

    @PutMapping("/{id}/block-user/{user-id}")
    @ApiOperation("Endpoint for blocking user inthe tournament. If user has been blocked, tournament is returned, else returns 4xx or 5xx code with error description")
    fun blockUserInTournament(user: User, @PathVariable("id") tournamentId: String, @PathVariable("user-id") userToBeBlockedId: Long) =
            tournamentManagementService.blockUserInTournament(user.id, tournamentId, userToBeBlockedId)

    @PutMapping("/{id}/unblock-user/{user-id}")
    @ApiOperation("Endpoint for unblocking user in the tournament. If user has been unblocked, tournament is returned, else returns 4xx or 5xx code with error description")
    fun unblockUserInTournament(user: User, @PathVariable("id") tournamentId: String, @PathVariable("user-id") userToBeUnblockedId: Long) =
            tournamentManagementService.unblockUserInTournament(user.id, tournamentId, userToBeUnblockedId)

    @PutMapping("/{id}/extend/{duration-in-sec}")
    @ApiOperation("Endpoint for extending tournament deadline. If tournament deadline has been extended, tournament is returned, else returns 4xx or 5xx code with error description")
    fun extendTournamentDeadline(user: User, @PathVariable("id") tournamentId: String, @PathVariable("duration-in-sec") durationInSec: Long) =
            tournamentManagementService.extendTournamentDeadline(user.id, tournamentId, Duration.ofSeconds(durationInSec))
}
