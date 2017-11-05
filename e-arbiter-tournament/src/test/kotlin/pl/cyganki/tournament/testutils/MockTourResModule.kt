package pl.cyganki.tournament.testutils

import pl.cyganki.utils.model.TaskUserDetails
import pl.cyganki.utils.model.tournamentresults.CodeTaskResultDto
import pl.cyganki.utils.model.tournamentresults.QuizTaskResultDto
import pl.cyganki.utils.model.tournamentresults.UserTournamentResults
import pl.cyganki.utils.model.tournamentresults.UsersTasksList
import pl.cyganki.utils.modules.TournamentResultsModuleInterface


class MockTourResModule : TournamentResultsModuleInterface {
    override fun getTasksUserDetails(taskIds: List<String>, tournamentId: String, userId: Long) = mapOf<String, TaskUserDetails>()

    override fun saveCodeTaskResult(codeTaskResultDto: CodeTaskResultDto) = throw UnsupportedOperationException("This mock shouldn't call this method")

    override fun saveQuizTaskResult(quizTaskResultDto: QuizTaskResultDto) = throw UnsupportedOperationException("This mock shouldn't call this method")

    override fun getTournamentResults(tournamentId: String, usersAndTasks: UsersTasksList) = listOf(UserTournamentResults())

    override fun getUserPlaceInTournament(tournamentId: String, userId: Long, usersAndTasks: UsersTasksList) = 1 // does not matter for our tests

    override fun getPdfReport(tournamentId: String, tournamentName: String, usersAndTasks: UsersTasksList) = throw UnsupportedOperationException("This mock shouldn't call this method")
}