package pl.cyganki.results.controller.inner

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import pl.cyganki.results.service.ResultService
import pl.cyganki.utils.model.tournamentresults.UsersTasksList

@RestController
@RequestMapping("/inner/results")
class ResultsController(private val resultService: ResultService) {

    // due to problems with passing bigger object in feign client, this request is made as a post
    @PostMapping("/{id}")
    @ApiOperation("Returns all results for tournament by passed id")
    fun getTournamentResults(@PathVariable("id") tournamentId: String, @RequestBody usersAndTasks: UsersTasksList)
            = resultService.getTournamentResults(tournamentId, usersAndTasks)

    // due to problems with passing bigger object in feign client, this request is made as a post
    @PostMapping("/user-place/{id}/{user-id}")
    @ApiOperation("Returns user's position with passed user-id in tournament with passed id")
    fun getUserPlaceInTournament(@PathVariable("id") tournamentId: String, @PathVariable("user-id") userId: Long, @RequestBody usersAndTasks: UsersTasksList)
            = resultService.getUserPlaceInTournament(tournamentId, userId, usersAndTasks)

}