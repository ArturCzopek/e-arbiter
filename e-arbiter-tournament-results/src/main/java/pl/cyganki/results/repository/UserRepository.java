package pl.cyganki.results.repository;

import org.springframework.data.repository.CrudRepository;
import pl.cyganki.results.model.database.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
