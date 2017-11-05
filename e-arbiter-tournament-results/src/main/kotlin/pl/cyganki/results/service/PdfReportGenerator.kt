package pl.cyganki.results.service

import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pl.cyganki.results.utils.Orientation
import pl.cyganki.results.utils.plusAssign
import pl.cyganki.utils.model.tournamentresults.UserTournamentResults
import pl.cyganki.utils.model.tournamentresults.UsersTasksList
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class PdfReportGenerator(private val resultService: ResultService) : ReportGenerator {

    @Value("\${e-arbiter.tmpFolder:tmp/}")
    lateinit var tmpFolder: String

    val removeFileDelay = 3000L

    val padding = 5.0f

    val baseBgColor = BaseColor(240, 240, 240)

    val maxTasksForHorizontalOrientation = 7

    val pageDimension = mapOf(
            Orientation.HORIZONTAL to "842 595",
            Orientation.VERTICAL to "595 842"
    )

    val placeColumnPercentWidth = mapOf(
            Orientation.HORIZONTAL to 0.03f,
            Orientation.VERTICAL to 0.06f
    )

    val userNameColumnPercentWidth = mapOf(
            Orientation.HORIZONTAL to 0.12f,
            Orientation.VERTICAL to 0.20f
    )

    val summaryResultColumnPercentWidth= mapOf(
            Orientation.HORIZONTAL to 0.08f,
            Orientation.VERTICAL to 0.12f
    )

    val fontSizes = mapOf(
            "title" to 24f,
            "paragraph" to 12f
    )

    val titleFont = Font(Font.FontFamily.HELVETICA, fontSizes["title"]!!, Font.BOLD)
    val boldParagraphFont = Font(Font.FontFamily.HELVETICA, fontSizes["paragraph"]!!, Font.BOLD)
    val baseParagraphFont = Font(Font.FontFamily.HELVETICA, fontSizes["paragraph"]!!, Font.NORMAL)

    override fun generate(tournamentId: String, tournamentName: String, usersAndTasks: UsersTasksList): File {

        val pageOrientation = if (usersAndTasks.tasks.size > maxTasksForHorizontalOrientation) Orientation.HORIZONTAL else Orientation.VERTICAL

        val createReportTime = LocalDateTime.now()
        var document = Document(PageSize.getRectangle(pageDimension[pageOrientation]))
        val filePath = generateFilePath(tournamentName, createReportTime)

        val reportFile = File(filePath)
        reportFile.parentFile.mkdirs()

        if (!reportFile.createNewFile()) {
            throw IOException("File $filePath cannot be created!")
        }

        val fileOutputStream = FileOutputStream(reportFile, false)
        PdfWriter.getInstance(document, fileOutputStream)

        document.apply {
            open()
            generateMetaData(document, tournamentName)
            generateTitle(document, tournamentName)
            generateResults(document, pageOrientation, tournamentId, usersAndTasks)
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

    private fun calculatePointsColumnWidth(columnsAmount: Int, orientation: Orientation)
            = (1f - placeColumnPercentWidth[orientation]!! - userNameColumnPercentWidth[orientation]!! - summaryResultColumnPercentWidth[orientation]!!) / columnsAmount

    private fun generateFilePath(name: String, date: LocalDateTime)
            = "$tmpFolder${name.replace(" ", "-")}_${with(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) { date.format(this) }}.pdf"

    private fun generateMetaData(document: Document, tournamentName: String) =
            document.apply {
                addTitle("Raport - $tournamentName")
                addAuthor("e-Arbiter")
                addCreator("e-Arbiter")
                addCreationDate()
            }

    private fun generateTitle(document: Document, tournamentName: String) = document.apply {
        this += Paragraph("Raport - $tournamentName", titleFont).apply {
            alignment = Element.ALIGN_CENTER
            spacingAfter = 35.0f
        }
    }

    private fun generateResults(document: Document, orientation: Orientation, tournamentId: String, usersAndTasks: UsersTasksList): Document {
        val placeColumnAmount = 1
        val userColumnAmount = 1
        val summaryPointsColumnAmount = 1

        val pointColumnSize = calculatePointsColumnWidth(usersAndTasks.tasks.size, orientation)

        var columnsPercentWidths = floatArrayOf(placeColumnPercentWidth[orientation]!!, userNameColumnPercentWidth[orientation]!!) +
                usersAndTasks.tasks.map { pointColumnSize } +
                summaryResultColumnPercentWidth[orientation]!!

        val columns = userColumnAmount + placeColumnAmount + usersAndTasks.tasks.size + summaryPointsColumnAmount
        val resultTable = PdfPTable(columns).apply {
            widthPercentage = 100f
            setWidths(columnsPercentWidths)
        }

        resultTable += generateTableHead(usersAndTasks.tasks.size)

        val results = resultService.getTournamentResults(tournamentId, usersAndTasks)

        for (result in results) {
            resultTable += generateOneResult(result)
        }

        document += resultTable
        return document
    }

    private fun generateTableHead(tasksSize: Int) = listOf(
            generateBoldTextCell("#"),
            generateBoldTextCell("Uzytkownik")
    ) +
            (1..tasksSize).map { generateBoldTextCell("#$it") } +
            generateBoldTextCell("W sumie")


    private fun generateOneResult(result: UserTournamentResults) = listOf(
            generateBoldTextCell("#${result.position}", baseBgColor),
            generateTextCell(result.userName)
    ) +
            result.taskResults.map { generateTextCell("${it.earnedPoints}") } +
            generateBoldTextCell("${result.summaryPoints}", baseBgColor)

    private fun generateBoldTextCell(cellValue: String, bgColor: BaseColor = BaseColor.LIGHT_GRAY) = generateTextCell(cellValue, boldParagraphFont, bgColor)

    private fun generateTextCell(cellValue: String, font: Font = baseParagraphFont, bgColor: BaseColor = baseBgColor) = PdfPCell().apply {
        paddingBottom = this@PdfReportGenerator.padding
        paddingTop = -this@PdfReportGenerator.padding
        verticalAlignment = Element.ALIGN_CENTER
        backgroundColor = bgColor
        this += Paragraph(cellValue, font)
    }

    companion object : KLogging()
}