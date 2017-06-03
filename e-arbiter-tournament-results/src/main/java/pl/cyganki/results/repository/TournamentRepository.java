package pl.cyganki.results.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cyganki.results.model.database.Tournament;

@Repository
public interface TournamentRepository extends CrudRepository<Tournament, Long> {
}

