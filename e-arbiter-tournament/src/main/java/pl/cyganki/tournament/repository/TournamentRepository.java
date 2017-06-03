package pl.cyganki.tournament.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.cyganki.tournament.model.Tournament;

@Repository
public interface TournamentRepository extends MongoRepository<Tournament, Long> {
}

