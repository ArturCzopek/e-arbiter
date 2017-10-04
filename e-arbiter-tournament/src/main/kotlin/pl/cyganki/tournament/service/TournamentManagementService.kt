package pl.cyganki.tournament.service

import org.springframework.stereotype.Service
import pl.cyganki.tournament.model.Tournament
import pl.cyganki.tournament.repository.TournamentRepository

@Service
class TournamentManagementService(val tournamentRepository: TournamentRepository) {

    fun saveTournament(userId: Long, tournament: Tournament): Tournament {
        tournament.ownerId = userId
        return this.tournamentRepository.save(tournament)
    }
}