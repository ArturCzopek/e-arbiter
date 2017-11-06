package pl.cyganki.results.service

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import mu.KLogging
import pl.cyganki.utils.model.tournamentresults.UsersTasksList
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface ReportGenerator {

    val fileExtension: String

    fun generate(tournamentId: String, tournamentName: String, usersAndTasks: UsersTasksList): File

    fun generateTitle(tournamentName: String) = "Raport - $tournamentName"

    fun asyncRemoveFile(reportFile: File) {
        launch(CommonPool) {
            delay(removeFileDelay)
            reportFile.run {
                delete()
                logger.debug { "Removed file ${this.name}" }
            }
        }
    }

    fun createReportFile(folder: String, tournamentName: String): File {
        val filePath = generateFilePath(folder, tournamentName)
        return File(filePath).apply {
            parentFile.mkdirs()
            if (!this.createNewFile()) {
                throw IOException("File $filePath cannot be created!")
            }
        }
    }

    fun getColumnsAmount(tasksNr: Int) = lazy {
        userColumnAmount + placeColumnAmount + tasksNr + summaryPointsColumnAmount
    }

    private fun generateFilePath(folder: String = "tmp/", name: String, date: LocalDateTime = LocalDateTime.now())
            = "$folder${name.replace(" ", "-")}_${with(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) { date.format(this) }}.${this.fileExtension}"

    companion object : KLogging() {
        private val removeFileDelay = 3000L

        val author = "e-Arbiter"

        val nrSuffix = "#"
        val placeColumnTitle = "#"
        val userColumnTitle = "Uzytkownik"
        val summaryColumnTitle = "W sumie"

        private val placeColumnAmount = 1
        private val userColumnAmount = 1
        private val summaryPointsColumnAmount = 1
    }
}