package pl.cyganki.tournament.exception

class InvalidTournamentIdException(id: String): RuntimeException("Tournament with id: $id does not exist!")