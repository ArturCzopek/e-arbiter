package pl.cyganki.results.service

import pl.cyganki.results.model.database.CodeTaskResult
import pl.cyganki.results.model.database.QuizTaskResult
import pl.cyganki.utils.model.tournamentresults.CodeTaskResultDto
import pl.cyganki.utils.model.tournamentresults.QuizTaskResultDto

object ResultBuilder {

    fun codeTaskResultFromDto(codeTaskResultDto: CodeTaskResultDto) = CodeTaskResult().apply {
        userId = codeTaskResultDto.userId
        tournamentId = codeTaskResultDto.tournamentId
        taskId = codeTaskResultDto.taskId
        setEarnedPoints(codeTaskResultDto.earnedPoints)
        language = codeTaskResultDto.language
        executionTime = codeTaskResultDto.executionTime
    }

    fun quizTaskResultFromDto(quizTaskResultDto: QuizTaskResultDto) = QuizTaskResult().apply {
        userId = quizTaskResultDto.userId
        tournamentId = quizTaskResultDto.tournamentId
        taskId = quizTaskResultDto.taskId
        setEarnedPoints(quizTaskResultDto.earnedPoints)
    }
}

