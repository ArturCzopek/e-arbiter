package pl.cyganki.tournament.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class UserIsAnOwnerException(userId: Long, tournamentId: String): RuntimeException("User $userId is an owner of tournament with id $tournamentId!")