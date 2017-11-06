package pl.cyganki.results.service

import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pl.cyganki.results.utils.Orientation
import pl.cyganki.results.utils.plusAssign
import pl.cyganki.utils.model.tournamentresults.UserTournamentResults
import pl.cyganki.utils.model.tournamentresults.UsersTasksList
import java.io.File
import java.io.FileOutputStream

@Service
class PdfReportGenerator(private val resultService: ResultService) : ReportGenerator {

    override val fileExtension = "pdf"

    @Value("\${e-arbiter.tmpFolder:tmp/}")
    lateinit var tmpFolder: String

    override fun generate(tournamentId: String, tournamentName: String, usersAndTasks: UsersTasksList): File {

        val pageOrientation = if (usersAndTasks.tasks.size > maxTasksForHorizontalOrientation) Orientation.HORIZONTAL else Orientation.VERTICAL

        var document = Document(PageSize.getRectangle(pageDimension[pageOrientation]))
        val reportFile = createReportFile(tmpFolder, tournamentName)

        FileOutputStream(reportFile, false).run {
            PdfWriter.getInstance(document, this)
        }

        document.apply {
            open()
            generateMetaData(document, tournamentName)
            generateTitle(document, tournamentName)
            generateResults(document, pageOrientation, tournamentId, usersAndTasks)
            close()
        }

        asyncRemoveFile(reportFile)

        logger.debug { "Generated report ${reportFile.name}" }
        return reportFile
    }

    private fun calculatePointsColumnWidth(columnsAmount: Int, orientation: Orientation)
            = (1f - placeColumnPercentWidth[orientation]!! - userNameColumnPercentWidth[orientation]!! - summaryResultColumnPercentWidth[orientation]!!) / columnsAmount

    private fun generateMetaData(document: Document, tournamentName: String) =
            document.apply {
                addTitle(generateTitle(tournamentName))
                addAuthor(ReportGenerator.author)
                addCreator(ReportGenerator.author)
                addCreationDate()
            }

    private fun generateTitle(document: Document, tournamentName: String) = document.apply {
        this += Paragraph(generateTitle(tournamentName), titleFont).apply {
            alignment = Element.ALIGN_CENTER
            spacingAfter = 35.0f
        }
    }

    private fun generateResults(document: Document, orientation: Orientation, tournamentId: String, usersAndTasks: UsersTasksList): Document {

        val pointColumnSize = calculatePointsColumnWidth(usersAndTasks.tasks.size, orientation)

        var columnsPercentWidths = floatArrayOf(placeColumnPercentWidth[orientation]!!, userNameColumnPercentWidth[orientation]!!) +
                usersAndTasks.tasks.map { pointColumnSize } +
                summaryResultColumnPercentWidth[orientation]!!

        val columns = getColumnsAmount(usersAndTasks.tasks.size).value

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

    private fun generateTableHead(tasksSize: Int) = (listOf(
            ReportGenerator.placeColumnTitle, ReportGenerator.userColumnTitle) +
            (1..tasksSize).map { "${ReportGenerator.nrSuffix}$it" } +
            ReportGenerator.summaryColumnTitle
            ).map { generateBoldTextCell(it) }


    private fun generateOneResult(result: UserTournamentResults) = listOf(
            generateBoldTextCell("${ReportGenerator.nrSuffix}${result.position}", baseBgColor),
            generateTextCell(result.userName)) +
            result.taskResults.map { generateTextCell("${it.earnedPoints}") } +
            generateBoldTextCell("${result.summaryPoints}", baseBgColor)

    private fun generateBoldTextCell(cellValue: String, bgColor: BaseColor = BaseColor.LIGHT_GRAY) = generateTextCell(cellValue, boldParagraphFont, bgColor)

    private fun generateTextCell(cellValue: String, font: Font = baseParagraphFont, bgColor: BaseColor = baseBgColor) = PdfPCell().apply {
        paddingBottom = PdfReportGenerator.padding
        paddingTop = -PdfReportGenerator.padding
        verticalAlignment = Element.ALIGN_CENTER
        backgroundColor = bgColor
        this += Paragraph(cellValue, font)
    }

    companion object : KLogging() {
        val padding = 5.0f
        val baseBgColor = BaseColor.WHITE
        val maxTasksForHorizontalOrientation = 7

        private val titleKey = "title"
        private val paragraphKey = "paragraph"

        private val fontSizes = mapOf(
                titleKey to 24f,
                paragraphKey to 12f
        )

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

        val summaryResultColumnPercentWidth = mapOf(
                Orientation.HORIZONTAL to 0.08f,
                Orientation.VERTICAL to 0.12f
        )

        val titleFont = Font(Font.FontFamily.HELVETICA, fontSizes[titleKey]!!, Font.BOLD)
        val boldParagraphFont = Font(Font.FontFamily.HELVETICA, fontSizes[paragraphKey]!!, Font.BOLD)
        val baseParagraphFont = Font(Font.FontFamily.HELVETICA, fontSizes[paragraphKey]!!, Font.NORMAL)
    }
}