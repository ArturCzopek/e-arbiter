package pl.cyganki.tournament.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import pl.cyganki.tournament.model.Tournament
import pl.cyganki.tournament.model.TournamentStatus
import pl.cyganki.tournament.model.dto.TournamentPreview
import pl.cyganki.tournament.repository.TournamentRepository
import pl.cyganki.utils.modules.AuthModuleInterface

@Service
class TournamentPreviewsFetcher(
        private val tournamentRepository: TournamentRepository,
        private val authModuleInterface: AuthModuleInterface
) {

    fun getActiveTournamentsInWhichUserParticipates(userId: Long, pageable: Pageable, query: String?) = getTournamentsInWhichUserParticipatesByStatus(userId, TournamentStatus.ACTIVE, pageable, query)

    fun getFinishedTournamentsInWhichUserParticipates(userId: Long, pageable: Pageable, query: String?) = getTournamentsInWhichUserParticipatesByStatus(userId, TournamentStatus.FINISHED, pageable, query)

    fun getActiveNewestTournamentsInWhichUserDoesNotParticipate(userId: Long, pageable: Pageable): Page<TournamentPreview> {
        val newestSort = Sort(Sort.Direction.DESC, "startDate")
        val pageRequest = PageRequest(pageable.pageNumber, pageable.pageSize, newestSort)
        return getTournamentsInWhichUserDoesNotParticipate(userId, pageRequest)
    }

    fun getActiveMostPopularTournamentsInWhichUserDoesNotParticipate(userId: Long, pageable: Pageable): Page<TournamentPreview> {
        val mostPopularSort = Sort(Sort.Direction.DESC, "joinedUsersIds.size")
        val pageRequest = PageRequest(pageable.pageNumber, pageable.pageSize, mostPopularSort)
        return getTournamentsInWhichUserDoesNotParticipate(userId, pageRequest)
    }

    fun getActiveAlmostEndedTournamentsInWhichUserDoesNotParticipate(userId: Long, pageable: Pageable): Page<TournamentPreview> {
        val almostEndedSort = Sort("endDate")
        val pageRequest = PageRequest(pageable.pageNumber, pageable.pageSize, almostEndedSort)
        return getTournamentsInWhichUserDoesNotParticipate(userId, pageRequest)
    }

    private fun getTournamentsInWhichUserParticipatesByStatus(userId: Long, status: TournamentStatus, pageable: Pageable, query: String?): Page<TournamentPreview> {
        val tournaments = getTournamentDependingOnQuery(userId, status, pageable, query)
        return tournaments.map {
            TournamentPreview(
                    it.id,
                    authModuleInterface.getUserNameById(it.ownerId),
                    it.name,
                    it.description,
                    it.isPublicFlag,
                    it.status,
                    it.joinedUsersIds.size
            )
        }
    }

    private fun getTournamentsInWhichUserDoesNotParticipate(userId: Long, pageable: Pageable): Page<TournamentPreview> {
        val tournaments = tournamentRepository.findAllPublicActiveTournamentsInWhichUserDoesNotParticipate(userId, pageable)
        return tournaments.map {
            TournamentPreview(
                    it.id,
                    authModuleInterface.getUserNameById(it.ownerId),
                    it.name,
                    it.description,
                    it.isPublicFlag,
                    it.status,
                    it.joinedUsersIds.size
            )
        }
    }

    private fun getTournamentDependingOnQuery(userId: Long, status: TournamentStatus, pageable: Pageable, query: String?): Page<Tournament> {
        return if (query == null || query.isEmpty()) {
            tournamentRepository.findAllTournamentsInWhichUserParticipatesByStatus(userId, status, pageable)
        } else {
            tournamentRepository.findAllTournamentsInWhichUserParticipatesByStatusAndQuery(userId, status, convertQueryToSQLLikeRegex(query), pageable)
        }
    }

    private fun convertQueryToSQLLikeRegex(query: String) = ".*$query.*"
}