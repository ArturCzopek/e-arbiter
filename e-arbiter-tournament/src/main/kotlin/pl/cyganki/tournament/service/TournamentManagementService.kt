package pl.cyganki.tournament.service

import org.springframework.stereotype.Service
import pl.cyganki.tournament.exception.IllegalTournamentStatusException
import pl.cyganki.tournament.exception.InvalidTournamentIdException
import pl.cyganki.tournament.exception.UserIsNotAnOwnerException
import pl.cyganki.tournament.model.Tournament
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.repository.TournamentRepository
import java.time.Duration

@Service
class TournamentManagementService(private val tournamentRepository: TournamentRepository) {

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
                    return tournamentRepository.save(this)
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

    fun removeUserFromTournament(requestAuthorId: Long, tournamentId: String, userToBeRemovedId: Long): Tournament {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {
            when {
                ownerId != requestAuthorId -> throw UserIsNotAnOwnerException(requestAuthorId, tournamentId)
                else -> {
                    this.removeUser(userToBeRemovedId)
                    return tournamentRepository.save(this)
                }
            }
        }
    }

    fun extendTournamentDeadline(userId: Long, tournamentId: String, extendDuration: Duration): Tournament {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {
            when {
                ownerId != userId -> throw UserIsNotAnOwnerException(userId, tournamentId)
                else -> {
                    extendDeadline(extendDuration)
                    return tournamentRepository.save(this)
                }
            }
        }
    }
}