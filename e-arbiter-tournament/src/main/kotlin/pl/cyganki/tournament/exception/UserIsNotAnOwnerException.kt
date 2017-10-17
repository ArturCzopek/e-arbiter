package pl.cyganki.tournament.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class UserIsNotAnOwnerException(userId: Long, tournamentId: String): RuntimeException("User $userId is not an owner of tournament with id $tournamentId!")