package pl.cyganki.results.service

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import mu.KLogging
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pl.cyganki.utils.model.tournamentresults.UserTournamentResults
import pl.cyganki.utils.model.tournamentresults.UsersTasksList
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Service
class XlsxReportGenerator(private val resultService: ResultService) : ReportGenerator {

    override val extension = "xlsx"

    @Value("\${e-arbiter.tmpFolder:tmp/}")
    lateinit var tmpFolder: String

    val removeFileDelay = 3000L

    override fun generate(tournamentId: String, tournamentName: String, usersAndTasks: UsersTasksList): File {
        val workbook = HSSFWorkbook()
        val document = workbook.createSheet("Raport - $tournamentName")

        val filePath = generateFilePath(tmpFolder, tournamentName)

        val reportFile = File(filePath)
        reportFile.parentFile.mkdirs()

        if (!reportFile.createNewFile()) {
            throw IOException("File $filePath cannot be created!")
        }

        document.apply {
            generateResults(document, tournamentId, usersAndTasks)
        }

        FileOutputStream(reportFile, false).run {
            workbook.write(this)
            flush()
            close()
        }

        launch(CommonPool) {
            delay(removeFileDelay)
            reportFile.delete()
            logger.debug { "Removed file ${reportFile.name}" }
        }

        logger.debug { "Generated report ${reportFile.name}" }
        return reportFile
    }

    private fun generateResults(document: HSSFSheet, tournamentId: String, usersAndTasks: UsersTasksList) {
//        val placeColumnAmount = 1
//        val userColumnAmount = 1
//        val summaryPointsColumnAmount = 1
//
//        val columns = userColumnAmount + placeColumnAmount + usersAndTasks.tasks.size + summaryPointsColumnAmount
//
        val results= resultService.getTournamentResults(tournamentId, usersAndTasks)

        results.forEachIndexed { rowNr, result ->
            generateOneResult(document, result, rowNr)
        }
    }

    private fun generateOneResult(document: HSSFSheet, result: UserTournamentResults, rowNr: Int) {
        val row = document.createRow(rowNr)
        val placeCell = row.createCell(0)
        placeCell.setCellValue("#${result.position}")
        val userNameCell = row.createCell(1)
        userNameCell.setCellValue(result.userName)
        result.taskResults.forEachIndexed { cellNr, taskResult ->
            val pointsCell = row.createCell(cellNr + 2)
            pointsCell.setCellValue("${taskResult.earnedPoints}")
        }
        val summaryCell = row.createCell(2 + result.taskResults.size)
        summaryCell.setCellValue("${result.summaryPoints}")
    }

    companion object : KLogging()
}