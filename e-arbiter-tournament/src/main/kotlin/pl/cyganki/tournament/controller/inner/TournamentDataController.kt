package pl.cyganki.tournament.controller.inner

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.tournament.service.TournamentDetailsService

@RestController
@RequestMapping("/inner")
class TournamentDataController(private val tournamentDetailsService: TournamentDetailsService) {

    @GetMapping("/users-tasks-list/{id}")
    fun getUsersTasksList(@PathVariable("id") tournamentId: String) = tournamentDetailsService.getUsersTasksList(tournamentId)
}