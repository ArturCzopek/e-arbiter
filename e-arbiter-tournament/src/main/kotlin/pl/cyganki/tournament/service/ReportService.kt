package pl.cyganki.tournament.service

import org.springframework.stereotype.Service
import pl.cyganki.tournament.exception.IllegalTournamentStatusException
import pl.cyganki.tournament.exception.InvalidTournamentIdException
import pl.cyganki.tournament.exception.UserIsNotAnOwnerException
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.utils.modules.TournamentResultsModuleInterface

@Service
class ReportService(
        private val tournamentRepository: TournamentRepository,
        private val tournamentDetailsService: TournamentDetailsService,
        private val tournamentResultsModuleInterface: TournamentResultsModuleInterface
) {

    fun getPdfReport(userId: Long, tournamentId: String) =
            with(tournamentRepository.findOne(tournamentId) ?: throw InvalidTournamentIdException(tournamentId)) {
                when {
                    ownerId != userId -> throw UserIsNotAnOwnerException(userId, tournamentId)
                    status != TournamentStatus.FINISHED -> throw IllegalTournamentStatusException(status, listOf(TournamentStatus.FINISHED))
                    else -> tournamentResultsModuleInterface.getPdfReport(tournamentId, name, tournamentDetailsService.getUsersTasksList(tournamentId))
                }
            }
}