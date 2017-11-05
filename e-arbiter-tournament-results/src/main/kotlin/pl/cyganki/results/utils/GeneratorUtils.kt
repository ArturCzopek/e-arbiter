package pl.cyganki.results.utils

import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable

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