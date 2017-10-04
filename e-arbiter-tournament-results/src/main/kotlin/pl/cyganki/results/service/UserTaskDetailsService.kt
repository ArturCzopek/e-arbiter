package pl.cyganki.results.service

import org.springframework.stereotype.Service
import pl.cyganki.results.exception.NotFoundResultException
import pl.cyganki.results.repository.ResultRepository
import pl.cyganki.utils.model.TaskUserDetails

@Service
class UserTaskDetailsService(private val resultRepository: ResultRepository) {

    fun getTaskUserDetails(taskId: String, userId: Long, maxAttempts: Int?): TaskUserDetails {

        val userResultsForTask = resultRepository.findAllByTaskIdAndUserId(taskId, userId!!)

        if (userResultsForTask.isEmpty()) throw NotFoundResultException(userId, taskId)

        return TaskUserDetails(
                earnedPoints = userResultsForTask.maxBy { it.earnedPoints }!!.earnedPoints,
                maxAttempts = maxAttempts,
                taskId = taskId,
                userAttempts = userResultsForTask.count()
        )
    }
}