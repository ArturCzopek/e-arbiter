package pl.cyganki.tournament.service

import org.springframework.stereotype.Service
import pl.cyganki.tournament.exception.IllegalTournamentStatusException
import pl.cyganki.tournament.exception.InvalidTournamentIdException
import pl.cyganki.tournament.model.QuizTask
import pl.cyganki.tournament.model.Task
import pl.cyganki.tournament.model.Tournament
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.model.dto.AccessDetails
import pl.cyganki.tournament.model.dto.TaskPreview
import pl.cyganki.tournament.model.dto.TournamentDetails
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.utils.model.TaskUserDetails
import pl.cyganki.utils.modules.AuthModuleInterface
import pl.cyganki.utils.modules.TournamentResultsModuleInterface

@Service
class TournamentDetailsService(
        val tournamentRepository: TournamentRepository,
        val authModuleInterface: AuthModuleInterface,
        val tournamentResultsModuleInterface: TournamentResultsModuleInterface
) {

    fun getTournamentDetailsForUser(userId: Long, tournamentId: String): TournamentDetails {
        val tournament: Tournament = tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)

        if (tournament.status == TournamentStatus.DRAFT && tournament.ownerId != userId) {
            throw IllegalTournamentStatusException(TournamentStatus.DRAFT, listOf(TournamentStatus.ACTIVE, TournamentStatus.FINISHED))
        }

        val accessDetails = tournament.run {
            AccessDetails(
                    isPublicFlag,
                    ownerId == userId,
                    joinedUsersIds.contains(userId),
                    isResultsVisibleForJoinedUsers
            )
        }

        if (!canSeeTournamentMainDetails(accessDetails)) {
            return tournament.run {
                TournamentDetails(
                        id,
                        authModuleInterface.getUserNameById(ownerId),
                        name,
                        status,
                        accessDetails
                )
            }
        }

        val tasksUserDetails = tournamentResultsModuleInterface.getTasksUserDetails(
                tournament.tasks.map { it.id },
                tournament.id,
                userId
        )

        val taskPreviews = tournament.tasks.map {
            TaskPreview(
                    it.id,
                    it.name,
                    it.description,
                    it.maxPoints,
                    if (it is QuizTask) "QUIZ" else "CODE",
                    getTaskUserDetails(it, tasksUserDetails, canSeeTaskFooter(accessDetails))
            )
        }

        return tournament.run {
            TournamentDetails(
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

    private fun getTaskUserDetails(task: Task, tasksUserDetails: Map<String, TaskUserDetails>, canSeeTaskFooter: Boolean): TaskUserDetails? {
        val taskUserDetails: TaskUserDetails?

        if (canSeeTaskFooter) {
            taskUserDetails = (tasksUserDetails[task.id] ?: TaskUserDetails())
            taskUserDetails.maxAttempts = (task as? QuizTask)?.maxAttempts
        } else {
            taskUserDetails = null
        }

        return taskUserDetails
    }

    private fun getTournamentMaxPoints(taskPreviews: List<TaskPreview>): Int {
        return taskPreviews
                .map { it.maxPoints }
                .reduce { total, taskMaxPoints -> total + taskMaxPoints }
    }

    private fun getEarnedPointsByUser(taskPreviews: List<TaskPreview>): Int {
        return taskPreviews
                .map { it.taskUserDetails }
                .map { it?.earnedPoints ?: 0 }
                .reduce { total, taskEarnedPoints -> total + taskEarnedPoints }
    }

    // user cannot see tournament details only if: tournament is not public and user is not an owner and user does not even participate
    private fun canSeeTournamentMainDetails(accessDetails: AccessDetails): Boolean =
            !(!accessDetails.publicFlag && !accessDetails.owner && !accessDetails.participateInTournament)

    private fun canSeeTaskFooter(accessDetails: AccessDetails) = accessDetails.participateInTournament
}