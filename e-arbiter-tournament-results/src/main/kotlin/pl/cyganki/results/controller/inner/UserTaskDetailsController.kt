package pl.cyganki.results.controller.inner

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.results.service.UserTaskDetailsService
import pl.cyganki.utils.model.TaskUserDetails

@RestController
@RequestMapping("/inner/user-details")
class UserTaskDetailsController(private val userTaskDetailsService: UserTaskDetailsService) {

    @GetMapping("/all")
    @ApiOperation("Returns data for user about his progress with all tasks in tournament by passed id and tasks with passed ids")
    fun getTasksUserDetails(
            @RequestParam("taskIds") taskIds: List<String>,
            @RequestParam("userId") userId: Long,
            @RequestParam("tournamentId") tournamentId: String
    ): Map<String, TaskUserDetails> = userTaskDetailsService.getAllTasksUserDetailsInTournament(tournamentId, taskIds, userId)
}
