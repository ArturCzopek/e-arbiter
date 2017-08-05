package pl.cyganki.tournament.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.utils.modules.AuthModuleInterface
import pl.cyganki.utils.security.dto.User

@Service
class TournamentPreviewsFetcher(
        val tournamentRepository: TournamentRepository,
        val authModuleInterface: AuthModuleInterface
) {

    fun getUsersActiveTournamentsInWhichParticipate(user: User, pageable: Pageable) {
        val tournament = tournamentRepository.findByJoinedUsersIdsContainsAndStatusAndOrderByEndDateDesc(user.id, TournamentStatus.ACTIVE, pageable)
        val ownerName = authModuleInterface.getOwnerName()
    }
}