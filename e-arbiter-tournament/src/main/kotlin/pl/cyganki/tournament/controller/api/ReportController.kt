package pl.cyganki.tournament.controller.api

import io.swagger.annotations.ApiOperation
import org.apache.commons.io.IOUtils
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.tournament.exception.InvalidResultsRightsException
import pl.cyganki.tournament.service.ReportService
import pl.cyganki.utils.security.dto.User
import java.io.FileInputStream

@RestController
@RequestMapping("/api/report")
class ReportController(private val reportService: ReportService) {

    @GetMapping("/pdf/{id}", produces = arrayOf(MediaType.APPLICATION_PDF_VALUE))
    @ApiOperation("Returns report as a pdf file for tournament with passed id")
    fun getPdfReportFromTournament(user: User, @PathVariable("id") tournamentId: String) =
            FileInputStream(reportService.getPdfReport(user.id, tournamentId)).use {
                IOUtils.toByteArray(it)
            } ?: throw InvalidResultsRightsException(user.id, tournamentId)
}
