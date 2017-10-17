package pl.cyganki.tournament.model.request

data class RemoveUserRequest(
        val requestAuthorId: Long = 0,
        val userToBeRemovedId: Long = 0,
        val tournamentId: String = ""
)