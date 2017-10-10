package pl.cyganki.results.service

import org.springframework.stereotype.Service
import pl.cyganki.results.model.database.CodeTaskResult
import pl.cyganki.results.model.database.Result
import pl.cyganki.results.repository.ResultRepository
import pl.cyganki.utils.modules.tournamentresult.dto.CodeTaskResultDto

@Service
class ResultService(private val resultRepository: ResultRepository) {

    fun saveResult(result: Result) {
        this.resultRepository.save(result)
    }

    fun saveCodeTaskResult(codeTaskResultDto: CodeTaskResultDto): Boolean {
        val codeTaskResult: CodeTaskResult =
                ResultBuilder.codeTaskResultFromDto(codeTaskResultDto)

        if (this.resultRepository.save(codeTaskResult) != null) {
            return true
        }

        return false
    }

}