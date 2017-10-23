package pl.cyganki.tournament.model.request

data class AdminMailRequest(
        val subject: String = "",
        val message: String = ""
)