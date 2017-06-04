package pl.cyganki.tournament.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.cyganki.tournament.model.QuestionTask;

public interface QuestionTaskRepository extends MongoRepository<QuestionTask, Long> {
}
