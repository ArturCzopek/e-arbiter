package pl.cyganki.tournament.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.cyganki.tournament.model.Task;

public interface TaskRepository extends MongoRepository<Task, Long> {
}
