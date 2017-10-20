package pl.cyganki.tournament.service

import org.springframework.stereotype.Service
import pl.cyganki.tournament.exception.IllegalTournamentStatusException
import pl.cyganki.tournament.exception.InvalidTournamentIdException
import pl.cyganki.tournament.exception.UserIsNotAnOwnerException
import pl.cyganki.tournament.exception.WrongUserParticipateStatusException
import pl.cyganki.tournament.model.Tournament
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.repository.TournamentRepository

@Service
class TournamentManagementService(private val tournamentRepository: TournamentRepository) {

    fun saveTournament(userId: Long, tournament: Tournament): Tournament {
        tournament.ownerId = userId
        return this.tournamentRepository.save(tournament)
    }

    fun findTournamentById(tournamentId: String): Tournament = this.tournamentRepository.findOne(tournamentId)

    fun findTournamentByIdAndOwnerId(tournamentId: String, requestAuthorId: Long): Tournament {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {

            when {
                ownerId != requestAuthorId -> throw UserIsNotAnOwnerException(requestAuthorId, tournamentId)
            }

            return this
        }
    }

    fun findTournamentByIdAndJoinedUserId(tournamentId: String, userId: Long): Tournament? {
        val tournament: Tournament = findTournamentById(tournamentId)
        val userIsEnrolled: Boolean = tournament.joinedUsersIds.contains(userId)

        return if (userIsEnrolled) tournament else null
    }

    fun removeUserFromTournament(requestAuthorId: Long, userToBeRemovedId: Long, tournamentId: String): Tournament {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {

            when {
                ownerId != requestAuthorId -> throw UserIsNotAnOwnerException(requestAuthorId, tournamentId)
                status != TournamentStatus.ACTIVE -> throw IllegalTournamentStatusException(status, listOf(TournamentStatus.ACTIVE))
                !joinedUsersIds.contains(userToBeRemovedId) -> throw WrongUserParticipateStatusException(userToBeRemovedId, tournamentId)
            }

            this.joinedUsersIds -= userToBeRemovedId
            return tournamentRepository.save(this)
        }
    }

    fun activateTournament(requestAuthorId: Long, tournamentId: String): Tournament {
        with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {

            when {
                ownerId != requestAuthorId -> throw UserIsNotAnOwnerException(requestAuthorId, tournamentId)
            }

            activate()
            return tournamentRepository.save(this)
        }
    }
}