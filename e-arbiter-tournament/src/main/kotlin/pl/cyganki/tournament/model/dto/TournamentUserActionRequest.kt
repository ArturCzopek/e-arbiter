package pl.cyganki.tournament.model.dto


data class TournamentUserActionRequest(
        val action: TournamentUserActionType,
        val tournamentId: String,
        val password: String? = null
)

enum class TournamentUserActionType {
    JOIN, LEAVE;
}