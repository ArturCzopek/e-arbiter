package pl.cyganki.tournament.exception

import pl.cyganki.tournament.model.TournamentStatus

class InvalidTournamentStatus(currentStatus: TournamentStatus, requiredStatus: TournamentStatus)
    : IllegalStateException("Invalid tournament status! Found: $currentStatus, required: $requiredStatus")
