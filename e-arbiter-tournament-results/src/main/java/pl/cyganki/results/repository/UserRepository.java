package pl.cyganki.results.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cyganki.results.model.database.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
