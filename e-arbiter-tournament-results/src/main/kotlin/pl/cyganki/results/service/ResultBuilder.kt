package pl.cyganki.results.service

import org.springframework.stereotype.Service
import pl.cyganki.results.model.database.CodeTaskResult
import pl.cyganki.utils.modules.tournamentresult.dto.CodeTaskResultDto

@Service
class ResultBuilder {

    companion object {
        fun codeTaskResultFromDto(codeTaskResultDto: CodeTaskResultDto): CodeTaskResult {
            val codeTaskResult = CodeTaskResult()

            codeTaskResult.userId = codeTaskResultDto.userId
            codeTaskResult.tournamentId = codeTaskResultDto.tournamentId
            codeTaskResult.taskId = codeTaskResultDto.taskId
            codeTaskResult.setEarnedPoints(codeTaskResultDto.earnedPoints)
            codeTaskResult.language = codeTaskResultDto.language
            codeTaskResult.executionTime = codeTaskResultDto.executionTime

            return codeTaskResult
        }
    }

}