package pl.cyganki.tournament.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.cyganki.tournament.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
}
