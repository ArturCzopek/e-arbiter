package pl.cyganki.tournament.exception


class WrongUserParticipateStatusException(tournamentId: String): RuntimeException("User has already joined/left tournament with id $tournamentId")
