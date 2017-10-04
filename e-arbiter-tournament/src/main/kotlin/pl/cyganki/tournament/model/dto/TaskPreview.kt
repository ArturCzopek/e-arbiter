package pl.cyganki.tournament.model.dto

data class TaskPreview(
        val name: String,
        val description: String,
        val maxPoints: Long,
        val earnedPoints: Float?,
        val userAttempts: Int?,
        val maxAttempts: Int?
)