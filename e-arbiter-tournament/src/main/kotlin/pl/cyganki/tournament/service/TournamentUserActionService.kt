package pl.cyganki.tournament.service

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import mu.KLogging
import org.springframework.stereotype.Service
import pl.cyganki.tournament.exception.*
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.model.request.TournamentUserActionRequest
import pl.cyganki.tournament.model.request.TournamentUserActionType
import pl.cyganki.tournament.repository.TournamentRepository

@Service
class TournamentUserActionService(
        private val tournamentRepository: TournamentRepository,
        private val hashingService: HashingService,
        private val mailService: MailService
) {

    fun joinToTournament(userId: Long, tournamentUserActionRequest: TournamentUserActionRequest): Boolean {
        with(tournamentRepository.findOne(tournamentUserActionRequest.tournamentId) ?: throw InvalidTournamentIdException(tournamentUserActionRequest.tournamentId)) {
            when {
                tournamentUserActionRequest.action != TournamentUserActionType.JOIN -> throw IllegalTournamentUserActionTypeException(tournamentUserActionRequest.action, listOf(TournamentUserActionType.JOIN))
                ownerId == userId -> throw UserIsAnOwnerException(userId, id)
                status != TournamentStatus.ACTIVE -> throw IllegalTournamentStatusException(status, listOf(TournamentStatus.ACTIVE))
                userId in joinedUsersIds || userId in blockedUsersIds -> throw WrongUserParticipateStatusException(userId, id)
                !isPublicFlag && !hashingService.checkPassword(tournamentUserActionRequest.password, password) -> throw IncorrectPasswordException()
            }

            joinedUsersIds += userId
            val savedTournament = tournamentRepository.save(this)

            if (userId in savedTournament.joinedUsersIds) {
                logger.info { "User $userId has joined to tournament $id" }
                launch(CommonPool) { mailService.sendJoinedToTournamentEmail(id, userId) }
            } else {
                throw RuntimeException("User $userId couldn't join to tournament $id")
            }

            return true
        }
    }

    fun leaveTournament(userId: Long, tournamentUserActionRequest: TournamentUserActionRequest): Boolean {
        with(tournamentRepository.findOne(tournamentUserActionRequest.tournamentId) ?: throw InvalidTournamentIdException(tournamentUserActionRequest.tournamentId)) {
            when {
                tournamentUserActionRequest.action != TournamentUserActionType.LEAVE -> throw IllegalTournamentUserActionTypeException(tournamentUserActionRequest.action, listOf(TournamentUserActionType.LEAVE))
                ownerId == userId -> throw UserIsAnOwnerException(userId, id)
                status != TournamentStatus.ACTIVE -> throw IllegalTournamentStatusException(status, listOf(TournamentStatus.ACTIVE))
                userId !in joinedUsersIds && userId !in blockedUsersIds -> throw WrongUserParticipateStatusException(userId, id)
            }

            if (userId in joinedUsersIds) {
                joinedUsersIds -= userId
            } else if (userId in blockedUsersIds) {
                blockedUsersIds -= userId
            }

            val savedTournament = tournamentRepository.save(this)

            if (!savedTournament.joinedUsersIds.contains(userId)) {
                logger.info { "User $userId has left tournament $id" }
            } else {
                throw RuntimeException("User $userId couldn't left tournament $id")
            }

            return true
        }
    }

    companion object: KLogging()
}