package pl.cyganki.tournament.service

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.springframework.stereotype.Service
import pl.cyganki.tournament.exception.IllegalTournamentStatusException
import pl.cyganki.tournament.exception.InvalidTournamentIdException
import pl.cyganki.tournament.exception.UserIsNotAnOwnerException
import pl.cyganki.tournament.model.Tournament
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.utils.modules.AuthModuleInterface
import pl.cyganki.utils.security.dto.User
import java.time.Duration

@Service
class TournamentManagementService(
        private val tournamentRepository: TournamentRepository,
        private val mailService: MailService,
        private val authModuleInterface: AuthModuleInterface
) {

    fun saveTournament(userId: Long, tournament: Tournament): Tournament {
        tournament.ownerId = userId
        return this.tournamentRepository.save(tournament)
    }

    fun findTournamentByIdAndOwnerId(tournamentId: String, requestAuthorId: Long): Tournament {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {
            when {
                ownerId != requestAuthorId -> throw UserIsNotAnOwnerException(requestAuthorId, tournamentId)
                else -> return this
            }
        }
    }

    fun activateTournament(requestAuthorId: Long, tournamentId: String): Tournament {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {
            when {
                ownerId != requestAuthorId -> throw UserIsNotAnOwnerException(requestAuthorId, tournamentId)
                else -> {
                    activate()
                    val savedTournament = tournamentRepository.save(this)
                    if (TournamentStatus.ACTIVE == savedTournament.status) {
                        launch(CommonPool) { mailService.sendActivatedTournamentEmail(tournamentId) }
                    } else {
                        throw RuntimeException("Tournament $tournamentId was not activated by user $requestAuthorId")
                    }

                    return savedTournament
                }
            }
        }
    }

    fun deleteTournament(userId: Long, tournamentId: String) {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {
            when {
                ownerId != userId -> throw UserIsNotAnOwnerException(userId, tournamentId)
                status != TournamentStatus.DRAFT -> throw IllegalTournamentStatusException(status, listOf(TournamentStatus.DRAFT))
                else -> tournamentRepository.delete(this)
            }
        }
    }

    fun getUsersEnrolledIntoTournament(userId: Long, tournamentId: String): List<User> {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {
            when {
                ownerId != userId -> throw UserIsNotAnOwnerException(userId, tournamentId)
                else -> return joinedUsersIds.map { User(it, authModuleInterface.getUserNameById(it)) }
            }
        }
    }

    fun removeUserFromTournament(requestAuthorId: Long, tournamentId: String, userToBeRemovedId: Long): Tournament {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {
            when {
                ownerId != requestAuthorId -> throw UserIsNotAnOwnerException(requestAuthorId, tournamentId)
                else -> {
                    this.removeUser(userToBeRemovedId)
                    val savedTournament = tournamentRepository.save(this)

                    if (!savedTournament.joinedUsersIds.contains(userToBeRemovedId)) {
                        launch(CommonPool) { mailService.sendRemovedUserFromTournamentEmail(tournamentId, userToBeRemovedId) }
                    } else {
                        throw RuntimeException("User $userToBeRemovedId was not removed from tournament $tournamentId by user $requestAuthorId")
                    }

                    return savedTournament
                }
            }
        }
    }

    fun extendTournamentDeadline(userId: Long, tournamentId: String, extendDuration: Duration): Tournament {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {
            when {
                ownerId != userId -> throw UserIsNotAnOwnerException(userId, tournamentId)
                else -> {
                    val oldDate = this.endDate
                    extendDeadline(extendDuration)
                    val savedTournament = tournamentRepository.save(this)

                    if (savedTournament.endDate.isAfter(oldDate)) {
                        launch(CommonPool) { mailService.sendExtendedTournamentDeadlineEmail(tournamentId) }
                    } else {
                        throw RuntimeException("Tournament $tournamentId end date was not extended by user $userId")
                    }

                    return savedTournament
                }
            }
        }
    }
}