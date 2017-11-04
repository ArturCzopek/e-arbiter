package pl.cyganki.results.controller.inner

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.results.service.ResultService
import pl.cyganki.utils.model.tournamentresults.CodeTaskResultDto
import pl.cyganki.utils.model.tournamentresults.QuizTaskResultDto

@RestController
@RequestMapping("/inner")
class ResultSaverController(private val resultService: ResultService) {

    @PostMapping("/code/save")
    fun saveCodeTaskResult(@RequestBody codeTaskResultDto: CodeTaskResultDto): Boolean {
        return resultService.saveCodeTaskResult(codeTaskResultDto)
    }

    @PostMapping("/quiz/save")
    fun saveQuizTaskResult(@RequestBody quizTaskResultDto: QuizTaskResultDto): Boolean {
        return resultService.saveQuizTaskResult(quizTaskResultDto)
    }
}