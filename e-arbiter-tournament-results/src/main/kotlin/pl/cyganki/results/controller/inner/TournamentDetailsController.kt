package pl.cyganki.results.controller.inner

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.results.service.ResultService

@RestController
@RequestMapping("/inner/results")
class TournamentDetailsController(private val resultService: ResultService) {

    @GetMapping("/{id}")
    @ApiOperation("Returns all results for tournament by passed id")
    fun getTournamentResults(@PathVariable("id") tournamentId: String) = resultService.getTournamentResults(tournamentId)

}