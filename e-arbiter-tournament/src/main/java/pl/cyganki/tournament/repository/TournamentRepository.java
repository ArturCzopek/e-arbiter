package pl.cyganki.tournament.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.cyganki.tournament.model.Tournament;

public interface TournamentRepository extends MongoRepository<Tournament, Long> {
}

