package pl.cyganki.results.service

import org.springframework.stereotype.Service
import pl.cyganki.results.repository.ResultRepository
import pl.cyganki.utils.modules.tournamentresult.dto.CodeTaskResultDto

@Service
class ResultService(private val resultRepository: ResultRepository) {

    fun saveCodeTaskResult(codeTaskResultDto: CodeTaskResultDto): Boolean {
        val codeTaskResult = ResultBuilder.codeTaskResultFromDto(codeTaskResultDto)

        this.resultRepository.save(codeTaskResult)?.let {
            return true
        }

        return false
    }
}