package pl.cyganki.results.controller.inner

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.results.service.ResultService
import pl.cyganki.utils.modules.tournamentresult.dto.CodeTaskResultDto

@RestController
@RequestMapping("/inner")
class ResultSaverController(private val resultService: ResultService) {

    @PostMapping("/code/save")
    fun saveCodeTaskResult(@RequestBody codeTaskResultDto: CodeTaskResultDto): Boolean {
        return resultService.saveCodeTaskResult(codeTaskResultDto)
    }
}