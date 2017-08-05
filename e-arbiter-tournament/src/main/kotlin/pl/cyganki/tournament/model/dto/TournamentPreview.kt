package pl.cyganki.tournament.model.dto

data class TournamentPreview(
    val id: String,
    val ownerName: String,
    val name: String,
    val description: String,
    val publicFlag: Boolean,
    val users: Int
)