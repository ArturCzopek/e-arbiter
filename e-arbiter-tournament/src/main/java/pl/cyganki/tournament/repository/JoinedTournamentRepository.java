package pl.cyganki.tournament.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.cyganki.tournament.model.JoinedTournament;

@Repository
public interface JoinedTournamentRepository extends MongoRepository<JoinedTournament, Long> {
}

