package pl.cyganki.results.service

import org.springframework.stereotype.Service
import pl.cyganki.results.repository.ResultRepository
import pl.cyganki.utils.model.tournamentresults.CodeTaskResultDto
import pl.cyganki.utils.model.tournamentresults.SingleTaskResult
import pl.cyganki.utils.model.tournamentresults.UserTournamentResults
import pl.cyganki.utils.model.tournamentresults.UsersTasksList
import pl.cyganki.utils.modules.AuthModuleInterface

@Service
class ResultService(
        private val userTaskDetailsService: UserTaskDetailsService,
        private val resultRepository: ResultRepository,
        private val authModuleInterface: AuthModuleInterface
) {

    fun saveCodeTaskResult(codeTaskResultDto: CodeTaskResultDto): Boolean {
        val codeTaskResult = ResultBuilder.codeTaskResultFromDto(codeTaskResultDto)

        this.resultRepository.save(codeTaskResult)?.let {
            return true
        }

        return false
    }

    fun getTournamentResults(tournamentId: String, usersAndTasks: UsersTasksList): List<UserTournamentResults> {

        val resultsWithPositions = mutableListOf<UserTournamentResults>()

        with(usersAndTasks) {

            val usersNames = authModuleInterface.getUserNamesByIds(users.toTypedArray())

            users.map { userId ->
                userTaskDetailsService.getAllUserTasksDetailsInTournament(tournamentId, tasks, userId).run {
                    UserTournamentResults(
                            userName = usersNames[userId]!!,
                            taskResults = this.map { SingleTaskResult(it.value.taskId, it.value.earnedPoints) },
                            summaryPoints = this.map { it.value.earnedPoints }.sum().toLong()
                    )
                }
            }
        }
                .sortedByDescending { it.summaryPoints }
                .forEachIndexed { index, details ->
                    with(details) {
                        resultsWithPositions += UserTournamentResults(
                                userName,
                                calculateUserPosition(index, this, resultsWithPositions),
                                taskResults,
                                summaryPoints
                        )
                    }
                }

        return resultsWithPositions
    }

    private fun calculateUserPosition(index: Int, details: UserTournamentResults, resultsWithPositions: List<UserTournamentResults>) =
            if (index > 0 && details.summaryPoints == resultsWithPositions[index - 1].summaryPoints) {
                resultsWithPositions[index - 1].position   // the same amount of points == the same position
            } else {
                index + 1    // reason is above: index is from 0, position is from 1
            }
}