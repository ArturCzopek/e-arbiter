package pl.cyganki.tournament.testutils

import pl.cyganki.utils.model.TaskUserDetails
import pl.cyganki.utils.modules.TournamentResultsModuleInterface


class MockTourResModule: TournamentResultsModuleInterface {
    override fun getTaskUserDetails(taskId: String, tournamentId: String, userId: Long) = TaskUserDetails()
}