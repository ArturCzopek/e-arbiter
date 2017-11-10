package pl.cyganki.tournament.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class WrongUserParticipateStatusException(userId: Long, tournamentId: String): RuntimeException("User $userId has already joined/left or is (un)blocked in tournament with id $tournamentId")
