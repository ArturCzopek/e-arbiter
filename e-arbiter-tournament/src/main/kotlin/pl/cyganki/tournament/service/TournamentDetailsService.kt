package pl.cyganki.tournament.service

import org.springframework.stereotype.Service
import pl.cyganki.tournament.exception.IllegalTournamentStatusException
import pl.cyganki.tournament.exception.InvalidTournamentIdException
import pl.cyganki.tournament.model.Tournament
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.model.dto.AccessDetails
import pl.cyganki.tournament.model.dto.TaskPreview
import pl.cyganki.tournament.model.dto.TournamentDetails
import pl.cyganki.tournament.repository.TournamentRepository
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

        val accessDetails = AccessDetails(
                publicFlag = tournament.isPublicFlag,
                owner = tournament.ownerId == userId,
                participateInTournament = tournament.joinedUsersIds.contains(userId),
                resultsVisible = tournament.isResultsVisibleForJoinedUsers
        )

        if (!canSeeTournamentMainDetails(accessDetails)) {
            return TournamentDetails(
                    id = tournament.id,
                    ownerName = authModuleInterface.getUserNameById(tournament.ownerId),
                    name = tournament.name,
                    status = tournament.status,
                    accessDetails = accessDetails
            )
        }
        var taskPreviews = tournament.tasks.map {
            TaskPreview(
                    it.name,
                    it.description,
                    it.maxPoints,
                    if (canSeeTaskFooter(accessDetails)) tournamentResultsModuleInterface.getTaskUserDetails(it.id, tournament.id, userId).apply { maxAttempts = null } else null
            )
        }
        return TournamentDetails(
                id = tournament.id,
                ownerName = authModuleInterface.getUserNameById(tournament.ownerId),
                name = tournament.name,
                status = tournament.status,
                accessDetails = accessDetails,
                description = tournament.description,
                users = tournament.joinedUsersIds.size,
                startDate = tournament.startDate,
                endDate = tournament.endDate,
                taskPreviews = taskPreviews,
                maxPoints = getTournamentMaxPoints(taskPreviews),
                earnedPoints = if (canSeeTaskFooter(accessDetails)) getEarnedPointsByUser(taskPreviews) else null
        )
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