package pl.cyganki.results.service

import pl.cyganki.utils.model.tournamentresults.UsersTasksList
import java.io.File

interface ReportGenerator {
    fun generate(tournamentId: String, tournamentName: String, usersAndTasks: UsersTasksList): File
}