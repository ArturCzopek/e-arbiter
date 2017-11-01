package pl.cyganki.results.service

import org.springframework.stereotype.Service
import pl.cyganki.results.repository.ResultRepository

@Service
class TournamentDetailsService(private val resultRepository: ResultRepository) {

    data class SingleTaskDetail(
            var taskId: String = "",
            var earnedPoints: Int = 0
    )

    data class UserTasksDetails(
            var userId: Long = 0,
            var tournamentTasks: List<SingleTaskDetail>,
            var summary: Int = (tournamentTasks.map { it.earnedPoints }).sum()
    )

    fun getTournamentDetails(tournamentId: String): List<UserTasksDetails> {

        val results = resultRepository.findAllByTournamentId(tournamentId)

        val tournamentUsers = (results.map { it.userId }).distinct()

        val tournamentTasks = (results.map { it.taskId }).distinct()

        return tournamentUsers.map {
            val user = it
            UserTasksDetails(
                    user,
                    tournamentTasks.map {
                        val task = it
                        with(results.filter { it.userId = user; it.taskId == task }) {
                            SingleTaskDetail(
                                    task,
                                    maxBy { it.earnedPoints }!!.earnedPoints
                            )
                        }
                    }
            )
        }
    }
}