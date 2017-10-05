package pl.cyganki.tournament.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import pl.cyganki.tournament.model.TournamentStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class IllegalTournamentStatusException(currentStatus: TournamentStatus, allowedStatuses: List<TournamentStatus>)
    : IllegalStateException("Invalid tournament status! Found: $currentStatus, allowed: ${allowedStatuses.joinToString(separator = ", ")}")
