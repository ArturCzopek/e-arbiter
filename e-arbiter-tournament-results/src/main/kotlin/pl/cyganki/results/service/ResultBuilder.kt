package pl.cyganki.results.service

import pl.cyganki.results.model.database.CodeTaskResult
import pl.cyganki.utils.modules.tournamentresult.dto.CodeTaskResultDto

object ResultBuilder {

    fun codeTaskResultFromDto(codeTaskResultDto: CodeTaskResultDto) = CodeTaskResult().apply {
        userId = codeTaskResultDto.userId
        tournamentId = codeTaskResultDto.tournamentId
        taskId = codeTaskResultDto.taskId
        setEarnedPoints(codeTaskResultDto.earnedPoints)
        language = codeTaskResultDto.language
        executionTime = codeTaskResultDto.executionTime
    }
}

