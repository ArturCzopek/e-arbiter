package pl.cyganki.tournament.testutils

import pl.cyganki.utils.model.TaskUserDetails
import pl.cyganki.utils.modules.TournamentResultsModuleInterface
import pl.cyganki.utils.modules.tournamentresult.dto.CodeTaskResultDto


class MockTourResModule: TournamentResultsModuleInterface {
    override fun getTaskUserDetails(taskId: String, tournamentId: String, userId: Long) = TaskUserDetails()

    override fun saveCodeTaskResult(codeTaskResultDto: CodeTaskResultDto) = throw UnsupportedOperationException("This mock shouldn't call this method")
}