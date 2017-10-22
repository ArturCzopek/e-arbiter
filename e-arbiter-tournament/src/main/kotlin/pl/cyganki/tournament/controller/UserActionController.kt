package pl.cyganki.tournament.controller

import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.tournament.model.request.TournamentUserActionRequest
import pl.cyganki.tournament.service.TournamentUserActionService
import pl.cyganki.utils.security.dto.User

@RestController
@RequestMapping("/api/user-action")
class UserActionController(private val tournamentUserActionService: TournamentUserActionService) {

    @PostMapping("/join")
    @ApiOperation("Endpoint for joining to an existing and active tournament.")
    fun joinToTournament(user: User, @RequestBody tournamentUserActionRequest: TournamentUserActionRequest): ResponseEntity<String> {
        tournamentUserActionService.joinToTournament(user.id, tournamentUserActionRequest)
        return ResponseEntity.ok("User has joined to tournament.")
    }

    @PostMapping("/leave")
    @ApiOperation("Endpoint for leaving from an existing and active tournament.")
    fun leaveTournament(user: User, @RequestBody tournamentUserActionRequest: TournamentUserActionRequest): ResponseEntity<String> {
        tournamentUserActionService.leaveTournament(user.id, tournamentUserActionRequest)
        return ResponseEntity.ok("User has left tournament.")
    }

}