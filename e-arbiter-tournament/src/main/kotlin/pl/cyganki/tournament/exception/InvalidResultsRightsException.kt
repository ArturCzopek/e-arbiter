package pl.cyganki.tournament.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidResultsRightsException(userId: Long, tournamentId: String): RuntimeException("User with id $userId cannot see results of tournament with id $tournamentId")