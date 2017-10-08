package pl.cyganki.tournament.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class WrongUserParticipateStatusException(tournamentId: String): RuntimeException("User has already joined/left tournament with id $tournamentId")
