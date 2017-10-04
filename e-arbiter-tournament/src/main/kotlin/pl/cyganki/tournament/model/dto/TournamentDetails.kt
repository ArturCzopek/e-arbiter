package pl.cyganki.tournament.model.dto

import pl.cyganki.tournament.model.TournamentStatus
import java.time.LocalDateTime

data class TournamentDetails(
        val id: String,
        val ownerName: String,
        val name: String,
        val status: TournamentStatus,
        val description: String? = null,
        val users: Int? = null,
        val startDate: LocalDateTime? = null,
        val endDate: LocalDateTime? = null,
        val taskPreviews: List<TaskPreview> = emptyList(),
        val maxPoints: Int? = null,
        val earnedPoints: Int? = null
)