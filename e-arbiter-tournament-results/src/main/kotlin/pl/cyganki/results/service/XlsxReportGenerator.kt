package pl.cyganki.results.service

import mu.KLogging
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pl.cyganki.results.utils.XlsxRowData
import pl.cyganki.results.utils.plusAssign
import pl.cyganki.utils.model.tournamentresults.UserTournamentResults
import pl.cyganki.utils.model.tournamentresults.UsersTasksList
import java.io.File
import java.io.FileOutputStream

@Service
class XlsxReportGenerator(private val resultService: ResultService) : ReportGenerator {

    override val fileExtension = "xlsx"

    @Value("\${e-arbiter.tmpFolder:tmp/}")
    lateinit var tmpFolder: String

    override fun generate(tournamentId: String, tournamentName: String, usersAndTasks: UsersTasksList): File {
        val workbook = HSSFWorkbook()
        val document = workbook.createSheet(generateTitle(tournamentName))
        val reportFile = createReportFile(tmpFolder, tournamentName)

        val headerStyle = generateHeaderStyle(workbook)
        val boldStyle = generateBoldStyle(workbook)

        val columns = getColumnsAmount(usersAndTasks.tasks.size).value

        document.apply {
            generateTableHead(document, usersAndTasks.tasks.size, headerStyle)
            generateResults(this, tournamentId, usersAndTasks, headerStyle, boldStyle)
            (0 until columns).forEach { this.autoSizeColumn(it) }
        }

        FileOutputStream(reportFile, false).run {
            workbook.write(this)
            flush()
            close()
        }

        asyncRemoveFile(reportFile)

        logger.debug { "Generated report ${reportFile.name}" }
        return reportFile
    }

    private fun generateTableHead(document: HSSFSheet, tasksSize: Int, headStyle: CellStyle) {
        val rows = listOf(ReportGenerator.placeColumnTitle, ReportGenerator.userColumnTitle) +
                (1..tasksSize).map { "${ReportGenerator.nrSuffix}$it" } +
                ReportGenerator.summaryColumnTitle

        val styles = rows.mapIndexed { index, _ -> index to headStyle }.toMap()

        document += XlsxRowData(0, rows, styles)
    }

    private fun generateResults(document: HSSFSheet, tournamentId: String, usersAndTasks: UsersTasksList, headerStyle: CellStyle, boldStyle: CellStyle) {
        resultService.getTournamentResults(tournamentId, usersAndTasks)
                .forEachIndexed { index, result ->
                    generateOneResult(document, result, index + 1, boldStyle)
                }
    }

    private fun generateOneResult(document: HSSFSheet, userResult: UserTournamentResults, rowNr: Int, boldStyle: CellStyle) {
        val rows = listOf("${ReportGenerator.nrSuffix}${userResult.position}", userResult.userName) +
                userResult.taskResults.map { "${it.earnedPoints}" } +
                "${userResult.summaryPoints}"

        val styles = mapOf(0 to boldStyle, 2 + userResult.taskResults.size to boldStyle)

        document += XlsxRowData(rowNr, rows, styles)
    }

    private fun generateBoldStyle(workbook: HSSFWorkbook): HSSFCellStyle {
        return workbook.createCellStyle().apply {
            setFont(workbook.createFont().apply { bold = true })
        }
    }

    private fun generateHeaderStyle(workbook: HSSFWorkbook): HSSFCellStyle {
        return workbook.createCellStyle().apply {
            setFont(workbook.createFont().apply { bold = true })
            setBorderBottom(BorderStyle.THIN)
            setBorderLeft(BorderStyle.THIN)
            setBorderRight(BorderStyle.THIN)
            fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
            setFillPattern(FillPatternType.SOLID_FOREGROUND)
        }
    }

    companion object : KLogging()

}
