package pl.cyganki.results.controller.inner

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import pl.cyganki.results.service.TournamentDetailsService

@RestController
@RequestMapping("/inner/tournament-details")
class TournamentDetailsController(private val tournamentDetailsService: TournamentDetailsService) {

    @GetMapping("/{tournamentId}")
    @ApiOperation("Returns result data about tournament by passed id")
    fun getTaskUserDetails (@PathVariable("tournamentId")  tournamentId: String) : List<TournamentDetailsService.UserTournamentDetails> {
       return(tournamentDetailsService.getTournamentDetails(tournamentId))
    }
}