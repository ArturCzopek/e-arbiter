package pl.cyganki.tournament.service

import org.apache.commons.lang3.StringUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import pl.cyganki.tournament.model.Tournament
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.model.dto.TournamentPreview
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.utils.modules.AuthModuleInterface

@Service
class TournamentPreviewsFetcher(
        val tournamentRepository: TournamentRepository,
        val authModuleInterface: AuthModuleInterface
) {

    fun getActiveTournamentsInWhichUserParticipates(userId: Long, pageable: Pageable, query: String) = getTournamentsInWhichUserParticipatesByStatus(userId, TournamentStatus.ACTIVE, pageable, query)

    fun getFinishedTournamentsInWhichUserParticipates(userId: Long, pageable: Pageable, query: String) = getTournamentsInWhichUserParticipatesByStatus(userId, TournamentStatus.FINISHED, pageable, query)

    private fun getTournamentsInWhichUserParticipatesByStatus(userId: Long, status: TournamentStatus, pageable: Pageable, query: String): Page<TournamentPreview> {
        val tournaments = getTournamentDependingOnQuery(query, userId, status, pageable)
        return tournaments.map {
            TournamentPreview(
                    it.id,
                    authModuleInterface.getUserNameById(it.ownerId),
                    it.name,
                    it.description,
                    it.isPublicFlag,
                    it.joinedUsersIds.size
            )
        }
    }

    private fun getTournamentDependingOnQuery(query: String, userId: Long, status: TournamentStatus, pageable: Pageable): Page<Tournament> {
        return if (StringUtils.isEmpty(query)) {
            tournamentRepository.findAllTournamentsWhereUserParticipateByStatus(userId, status, pageable)
        } else {
            tournamentRepository.findAllTournamentsWhereUserParticipateByStatusAndQuery(userId, status, query, pageable)
        }
    }
}