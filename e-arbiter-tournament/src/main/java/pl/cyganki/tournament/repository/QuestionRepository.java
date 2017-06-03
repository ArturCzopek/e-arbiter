package pl.cyganki.tournament.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.cyganki.tournament.model.Question;


@Repository
public interface QuestionRepository extends MongoRepository<Question, Long> {
}
