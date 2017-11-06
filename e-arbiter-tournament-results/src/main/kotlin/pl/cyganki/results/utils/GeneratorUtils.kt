package pl.cyganki.results.utils

import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.ss.usermodel.CellStyle

/*
 * PDF
 */

operator fun Document.plusAssign(element: Element) {
    this.add(element)
}

operator fun PdfPTable.plusAssign(cells: List<PdfPCell>) {
    cells.forEach { this += it }
}

operator fun PdfPTable.plusAssign(cell: PdfPCell) {
    this.addCell(cell)
}

operator fun PdfPCell.plusAssign(element: Element) {
    this.addElement(element)
}

/*
 * XLSX
 */

data class XlsxRowData(
        val rowNr: Int,
        val cellsTexts: List<String>,
        val styles: Map<Int, CellStyle> = mapOf()
)

fun HSSFSheet.addRow(rowData: XlsxRowData) {
    rowData.run {
        val row = this@addRow.createRow(rowNr)
        cellsTexts.forEachIndexed { index, cellValue ->
            with(row.createCell(index)) {
                setCellValue(cellValue)
                (index in styles).let { setCellStyle(styles[index]) }
            }
        }
    }
}

operator fun HSSFSheet.plusAssign(rowData: XlsxRowData) {
    this.addRow(rowData)
}