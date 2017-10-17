package pl.cyganki.tournament.controller

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.tournament.model.Tournament
import pl.cyganki.tournament.model.request.RemoveUserRequest
import pl.cyganki.tournament.service.TournamentManagementService
import pl.cyganki.utils.security.dto.User
import javax.validation.Valid

@RestController
@RequestMapping("/api/management")
class TournamentManagementController(private val tournamentManagementService: TournamentManagementService) {

    @PostMapping("/save")
    @ApiOperation("Endpoint for adding a new tournament. If is ok, then returns added tournament, else returns 4xx or 5xx code with error description")
    fun saveTournament(user: User, @RequestBody @Valid tournament: Tournament) = tournamentManagementService.saveTournament(user.id, tournament)

    @PostMapping("/remove-user")
    @ApiOperation("Endpoint for removing user from the tournament. If user has been removed, tournament is returned, else returns 4xx or 5xx code with error description")
    fun removeUserFromTournament(@RequestBody removeUserRequest: RemoveUserRequest) = with(removeUserRequest) {
        tournamentManagementService.removeUserFromTournament(requestAuthorId, userToBeRemovedId, tournamentId)
    }
}
