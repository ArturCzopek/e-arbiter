package pl.cyganki.results.service

import org.springframework.stereotype.Service
import pl.cyganki.results.repository.ResultRepository
import pl.cyganki.utils.model.TaskUserDetails

@Service
class UserTaskDetailsService(private val resultRepository: ResultRepository) {

    fun getTaskUserDetails(tournamentId: String, taskId: String, userId: Long): TaskUserDetails {

        val userResultsForTask = resultRepository.findAllByTournamentIdAndTaskIdAndUserId(tournamentId, taskId, userId)

        return TaskUserDetails(
                earnedPoints = if (userResultsForTask.isNotEmpty()) userResultsForTask.maxBy { it.earnedPoints }!!.earnedPoints else 0,
                taskId = taskId,
                userAttempts = userResultsForTask.size
        )
    }
}