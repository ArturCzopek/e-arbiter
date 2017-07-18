package pl.cyganki.tournament.exception

import pl.cyganki.tournament.model.TournamentStatus

class IllegalTournamentStatusException(currentStatus: TournamentStatus, allowedStatuses: List<TournamentStatus>)
    : IllegalStateException("Invalid tournament status! Found: $currentStatus, allowed: ${allowedStatuses.joinToString(separator = ", ")}")