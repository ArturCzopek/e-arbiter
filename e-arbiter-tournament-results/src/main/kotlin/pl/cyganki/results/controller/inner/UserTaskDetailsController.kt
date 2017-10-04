package pl.cyganki.results.controller.inner

import io.swagger.annotations.ApiOperation
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.results.service.UserTaskDetailsService
import pl.cyganki.utils.model.TaskUserDetails

@RestController
@RequestMapping("/inner/user-details")
class UserTaskDetailsController(private val userTaskDetailsService: UserTaskDetailsService) {

    @GetMapping(value = *arrayOf("/", ""))
    @Cacheable(value = "task-user-details", key = "#taskId + ' ' + #userId")   // TODO: clean cache when new solution is uploaded
    @ApiOperation("Returns data for user about his progress with task by passed id")
    fun getTaskUserDetails(
            @RequestParam("taskId") taskId: String,
            @RequestParam("userId") userId: Long,
            @RequestParam("maxAttempts") maxAttempts: Int?
    ): TaskUserDetails = userTaskDetailsService.getTaskUserDetails(taskId, userId, maxAttempts)
}
