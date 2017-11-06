package pl.cyganki.results.service

import pl.cyganki.utils.model.tournamentresults.UsersTasksList
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface ReportGenerator {

    val extension: String

    fun generate(tournamentId: String, tournamentName: String, usersAndTasks: UsersTasksList): File

    fun generateFilePath(folder: String = "tmp/", name: String, date: LocalDateTime = LocalDateTime.now())
            = "$folder${name.replace(" ", "-")}_${with(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) { date.format(this) }}.${this.extension}"
}