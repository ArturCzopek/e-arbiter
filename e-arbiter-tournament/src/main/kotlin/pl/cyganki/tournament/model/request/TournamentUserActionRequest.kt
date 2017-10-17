package pl.cyganki.tournament.model.request

data class TournamentUserActionRequest(
        var action: TournamentUserActionType = TournamentUserActionType.JOIN,
        var tournamentId: String = "",
        var password: String? = null
)

enum class TournamentUserActionType {
    JOIN, LEAVE;
}