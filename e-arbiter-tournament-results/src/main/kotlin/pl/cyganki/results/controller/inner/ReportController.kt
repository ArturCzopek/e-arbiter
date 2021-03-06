package pl.cyganki.results.controller.inner

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import pl.cyganki.results.service.ReportGenerator
import pl.cyganki.utils.model.tournamentresults.UsersTasksList

@RestController
@RequestMapping("/inner/report")
class ReportController(
        private val pdfReportGenerator: ReportGenerator,
        private val xlsxReportGenerator: ReportGenerator
) {

    @PostMapping("/pdf/{id}/{name}")
    @ApiOperation("Returns report as a pdf file for tournament with passed id for passed data")
    fun getPdfReport(@PathVariable("id") tournamentId: String, @PathVariable("name") tournamentName: String, @RequestBody usersAndTasks: UsersTasksList)
            = pdfReportGenerator.generate(tournamentId, tournamentName, usersAndTasks)

    @PostMapping("/xlsx/{id}/{name}")
    @ApiOperation("Returns report as a xlsx file for tournament with passed id for passed data")
    fun getXlsxReport(@PathVariable("id") tournamentId: String, @PathVariable("name") tournamentName: String, @RequestBody usersAndTasks: UsersTasksList)
            = xlsxReportGenerator.generate(tournamentId, tournamentName, usersAndTasks)
}