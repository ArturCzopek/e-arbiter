package pl.cyganki.tournament.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import pl.cyganki.tournament.model.request.TournamentUserActionType

@ResponseStatus(HttpStatus.BAD_REQUEST)
class IllegalTournamentUserActionTypeException(currentStatus: TournamentUserActionType, allowedStatuses: List<TournamentUserActionType>)
    : IllegalStateException("Invalid tournament user action type! Found: $currentStatus, allowed: ${allowedStatuses.joinToString(separator = ", ")}")