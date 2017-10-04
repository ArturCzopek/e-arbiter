package pl.cyganki.tournament.model.dto

import pl.cyganki.tournament.model.TournamentStatus
import java.time.LocalDateTime

data class TournamentDetails(
        val id: String,
        val ownerName: String,
        val name: String,
        val status: TournamentStatus,
        val description: String?,
        val users: Int?,
        val startDate: LocalDateTime?,
        val endDate: LocalDateTime?,
        val taskPreviews: List<TaskPreview>?,
        val maxPoints: Int?,
        val earnedPoints: Int?
)