package pl.cyganki.results.service

import org.springframework.stereotype.Service
import pl.cyganki.results.model.database.Result
import pl.cyganki.results.repository.ResultRepository

@Service
class ResultService(private val resultRepository: ResultRepository) {

    fun saveResult(result: Result) {
        this.resultRepository.save(result)
    }

}