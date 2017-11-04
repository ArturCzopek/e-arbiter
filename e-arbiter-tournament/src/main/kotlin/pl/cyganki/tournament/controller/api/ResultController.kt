package pl.cyganki.tournament.controller.api

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.tournament.service.TournamentDetailsService
import pl.cyganki.utils.security.dto.User

@RestController
@RequestMapping("/api/results")
class ResultController(private val tournamentDetailsService: TournamentDetailsService) {

    @GetMapping("/{id}")
    @ApiOperation("Returns all results for tournament if user is allowed to do it")
    fun getResultsForTournament(user: User, @PathVariable("id") tournamentId: String) = tournamentDetailsService.getTournamentResults(user.id, tournamentId)
}