package pl.cyganki.results.service

import org.springframework.stereotype.Service
import pl.cyganki.results.repository.ResultRepository
import pl.cyganki.utils.model.tournamentresults.CodeTaskResultDto
import pl.cyganki.utils.model.tournamentresults.QuizTaskResultDto
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

    fun saveQuizTaskResult(quizTaskResultDto: QuizTaskResultDto): Boolean {
        val quizTaskResult = ResultBuilder.quizTaskResultFromDto(quizTaskResultDto)

        this.resultRepository.save(quizTaskResult)?.let {
            return true
        }

        return false
    }

    fun getTournamentResults(tournamentId: String, usersAndTasks: UsersTasksList) =
            updatePositions {
                getAllSortedResultsForTournamentWithoutPositions(usersAndTasks, tournamentId)
            }

    fun getUserPlaceInTournament(tournamentId: String, userId: Long, usersAndTasks: UsersTasksList): Int {
        val userName = authModuleInterface.getUserNameById(userId)
        return getTournamentResults(tournamentId, usersAndTasks).first { it.userName == userName }.position
    }

    private fun updatePositions(results: () -> List<UserTournamentResults>): MutableList<UserTournamentResults> {

        val resultsWithPositions = mutableListOf<UserTournamentResults>()

        results().forEachIndexed { index, details ->
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

    private fun getAllSortedResultsForTournamentWithoutPositions(usersAndTasks: UsersTasksList, tournamentId: String) = with(usersAndTasks) {
        val usersNames = authModuleInterface.getUserNamesByIds(users.toTypedArray())

        users.map { userId ->
            userTaskDetailsService.getAllUserTasksDetailsInTournament(tournamentId, tasks, userId).run {
                UserTournamentResults(
                        userName = usersNames[userId]!!,
                        taskResults = this.map { pl.cyganki.utils.model.tournamentresults.SingleTaskResult(it.value.taskId, it.value.earnedPoints) },
                        summaryPoints = this.map { it.value.earnedPoints }.sum().toLong()
                )
            }
        }
    }
            .sortedBy { it.userName }
            .sortedByDescending { it.summaryPoints }


    private fun calculateUserPosition(index: Int, details: UserTournamentResults, resultsWithPositions: List<UserTournamentResults>) =
            if (index > 0 && details.summaryPoints == resultsWithPositions[index - 1].summaryPoints) {
                resultsWithPositions[index - 1].position   // the same amount of points == the same position
            } else {
                index + 1    // reason is above: index is from 0, position is from 1
            }
}