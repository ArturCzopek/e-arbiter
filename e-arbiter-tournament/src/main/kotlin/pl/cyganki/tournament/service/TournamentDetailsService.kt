package pl.cyganki.tournament.service

import org.springframework.stereotype.Service
import pl.cyganki.tournament.exception.IllegalTournamentStatusException
import pl.cyganki.tournament.exception.InvalidResultsRightsException
import pl.cyganki.tournament.exception.InvalidTournamentIdException
import pl.cyganki.tournament.model.QuizTask
import pl.cyganki.tournament.model.Task
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.model.dto.AccessDetails
import pl.cyganki.tournament.model.dto.TaskPreview
import pl.cyganki.tournament.model.dto.TournamentDetails
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.utils.model.TaskUserDetails
import pl.cyganki.utils.model.tournamentresults.UsersTasksList
import pl.cyganki.utils.modules.AuthModuleInterface
import pl.cyganki.utils.modules.TournamentResultsModuleInterface

@Service
class TournamentDetailsService(
        private val tournamentRepository: TournamentRepository,
        private val authModuleInterface: AuthModuleInterface,
        private val tournamentResultsModuleInterface: TournamentResultsModuleInterface
) {

    fun getTournamentDetailsForUser(userId: Long, tournamentId: String): TournamentDetails {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {

            if (status == TournamentStatus.DRAFT && ownerId != userId) {
                throw IllegalTournamentStatusException(TournamentStatus.DRAFT, listOf(TournamentStatus.ACTIVE, TournamentStatus.FINISHED))
            }

            val accessDetails = AccessDetails(
                    isPublicFlag,
                    ownerId == userId,
                    userId in joinedUsersIds,
                    isResultsVisibleForJoinedUsers
            )

            if (!canSeeTournamentMainDetails(accessDetails)) {
                return TournamentDetails(
                        id,
                        authModuleInterface.getUserNameById(ownerId),
                        name,
                        status,
                        accessDetails
                )
            }

            val tasksUserDetails = tournamentResultsModuleInterface.getTasksUserDetails(
                    tasks.map { it.id },
                    id,
                    userId
            )

            val taskPreviews = tasks.map {
                TaskPreview(
                        it.id,
                        it.name,
                        it.description,
                        it.maxPoints,
                        if (it is QuizTask) "QUIZ" else "CODE",
                        getTaskUserDetails(it, tasksUserDetails, canSeeTaskFooter(accessDetails))
                )
            }

            return TournamentDetails(
                    id,
                    authModuleInterface.getUserNameById(ownerId),
                    name,
                    status,
                    accessDetails,
                    description,
                    joinedUsersIds.size,
                    startDate,
                    endDate,
                    taskPreviews,
                    getTournamentMaxPoints(taskPreviews),
                    if (canSeeTaskFooter(accessDetails)) getEarnedPointsByUser(taskPreviews) else null
            )
        }
    }

    fun getTournamentResults(userId: Long, tournamentId: String) =
            if (canSeeResults(getTournamentDetailsForUser(userId, tournamentId).accessDetails)) {
                tournamentResultsModuleInterface.getTournamentResults(tournamentId, getUsersTasksList(tournamentId))
            } else {
                throw InvalidResultsRightsException(userId, tournamentId)
            }

    private fun getUsersTasksList(tournamentId: String) =
            with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {
                UsersTasksList(
                        joinedUsersIds,
                        tasks.map { it.id }
                )
            }

    private fun getTaskUserDetails(task: Task, tasksUserDetails: Map<String, TaskUserDetails>, canSeeTaskFooter: Boolean) =
            if (canSeeTaskFooter) {
                (tasksUserDetails[task.id] ?: TaskUserDetails()).apply { maxAttempts = (task as? QuizTask)?.maxAttempts }
            } else {
                null
            }

    private fun getTournamentMaxPoints(taskPreviews: List<TaskPreview>) = taskPreviews
            .map { it.maxPoints }
            .reduce { total, taskMaxPoints -> total + taskMaxPoints }

    private fun getEarnedPointsByUser(taskPreviews: List<TaskPreview>) = taskPreviews
            .map { it.taskUserDetails }
            .map { it?.earnedPoints ?: 0 }
            .reduce { total, taskEarnedPoints -> total + taskEarnedPoints }

    // user cannot see tournament details only if: tournament is not public and user is not an owner and user does not even participate
    private fun canSeeTournamentMainDetails(accessDetails: AccessDetails) =
            !(!accessDetails.publicFlag && !accessDetails.owner && !accessDetails.participateInTournament)

    private fun canSeeTaskFooter(accessDetails: AccessDetails) = accessDetails.participateInTournament

    private fun canSeeResults(accessDetails: AccessDetails) =
            accessDetails.resultsVisible && accessDetails.participateInTournament || accessDetails.owner
}