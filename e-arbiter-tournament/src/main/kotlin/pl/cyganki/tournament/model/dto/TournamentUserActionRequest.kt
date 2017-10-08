package pl.cyganki.tournament.model.dto


data class TournamentUserActionRequest(
        val action: TournamentUserActionType,
        val tournamentId: String,
        val password: String?
)

enum class TournamentUserActionType {
    JOIN, LEAVE;
}