package pl.cyganki.tournament.service

import org.springframework.stereotype.Service
import pl.cyganki.tournament.exception.*
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.model.dto.TournamentUserActionRequest
import pl.cyganki.tournament.model.dto.TournamentUserActionType
import pl.cyganki.tournament.repository.TournamentRepository

@Service
data class TournamentUserActionService(
        private val tournamentRepository: TournamentRepository,
        private val hashingService: HashingService
) {

    fun joinToTournament(userId: Long, tournamentUserActionRequest: TournamentUserActionRequest): Boolean {
        val tournament = tournamentUserActionRequest.run {
            tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        }

        when {
            tournamentUserActionRequest.action != TournamentUserActionType.JOIN -> throw IllegalTournamentUserActionTypeException(tournamentUserActionRequest.action, listOf(TournamentUserActionType.JOIN))
            tournament.ownerId == userId -> throw UserIsAnOwnerException()
            tournament.status != TournamentStatus.ACTIVE -> throw IllegalTournamentStatusException(tournament.status, listOf(TournamentStatus.ACTIVE))
            tournament.joinedUsersIds.contains(userId) -> throw WrongUserParticipateStatusException(tournament.id)
            !tournament.isPublicFlag && !hashingService.checkPassword(tournamentUserActionRequest.password, tournament.password) -> throw IncorrectPasswordException()
        }

        tournament.joinedUsersIds += userId
        tournamentRepository.save(tournament)

        return true
    }

    fun leaveTournament(userId: Long, tournamentUserActionRequest: TournamentUserActionRequest): Boolean {
        val tournament = tournamentUserActionRequest.run {
            tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)
        }

        when {
            tournamentUserActionRequest.action != TournamentUserActionType.LEAVE -> throw IllegalTournamentUserActionTypeException(tournamentUserActionRequest.action, listOf(TournamentUserActionType.LEAVE))
            tournament.ownerId == userId -> throw UserIsAnOwnerException()
            tournament.status != TournamentStatus.ACTIVE -> throw IllegalTournamentStatusException(tournament.status, listOf(TournamentStatus.ACTIVE))
            !tournament.joinedUsersIds.contains(userId) -> throw WrongUserParticipateStatusException(tournament.id)
        }

        tournament.joinedUsersIds -= userId
        tournamentRepository.save(tournament)

        return true
    }
}