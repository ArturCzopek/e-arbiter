package pl.cyganki.tournament.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class UserIsAnOwnerException: RuntimeException("User cannot be an owner!")