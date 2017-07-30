package pl.cyganki.tournament.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.cyganki.tournament.model.Question;

public interface QuestionTaskRepository extends MongoRepository<Question, Long> {
}
