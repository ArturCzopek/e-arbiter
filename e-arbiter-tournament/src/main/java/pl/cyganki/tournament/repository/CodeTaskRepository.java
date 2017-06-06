package pl.cyganki.tournament.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.cyganki.tournament.model.CodeTask;

public interface CodeTaskRepository extends MongoRepository<CodeTask, Long> {
}
