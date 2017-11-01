package pl.cyganki.results.service

import org.springframework.stereotype.Service
import pl.cyganki.results.repository.ResultRepository

@Service
class TournamentDetailsService(private val resultRepository: ResultRepository) {

    data class SingleTaskDetail(
            var taskId: String,
            var earnedPoints: Int
    )

    data class UserTasksDetails(
            var userId: Long,
            var tournamentTasks: List<SingleTaskDetail>,
            var summary: Int = tournamentTasks.sumBy { it.earnedPoints }
    )

    data class UserTournamentDetails(
            var userId: Long,
            var tournamentTasks: List<SingleTaskDetail>,
            var summary: Int = tournamentTasks.sumBy { it.earnedPoints },
            var place : Int
    )

    fun getTournamentDetails(tournamentId: String): List<UserTournamentDetails> {

        val results = resultRepository.findAllByTournamentId(tournamentId)

        val tournamentUsers = (results.map { it.userId }).distinct()

        val tournamentTasks = (results.map { it.taskId }).distinct()

        val test = tournamentUsers.map {
            val user = it
            UserTasksDetails(
                    user,
                    tournamentTasks.map {
                        SingleTaskDetail(
                                it,
                                results.filter { r -> r.taskId == it && r.userId == user }.map{ it.earnedPoints }.max()!!
                        )
                    }
            )
        }

        var nowCount = 1
        var prevCount = nowCount
        var counter = 0

        return test.sortedByDescending { it.summary }.groupBy { it.summary }
               .map{ prevCount = nowCount ; nowCount += it.value.size ; counter++ ;
                   if (counter == 1 ) it.value.map{ UserTournamentDetails (it.userId, it.tournamentTasks, it.summary, 1) } else
                       it.value.map{ UserTournamentDetails (it.userId, it.tournamentTasks, it.summary, prevCount) } }.flatten()
    }
}