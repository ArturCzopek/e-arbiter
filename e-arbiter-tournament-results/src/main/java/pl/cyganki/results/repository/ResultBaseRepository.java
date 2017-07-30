package pl.cyganki.results.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.cyganki.results.model.database.Result;

import java.util.List;

@NoRepositoryBean
public interface ResultBaseRepository<T extends Result> extends JpaRepository<T, Long> {
    List<Result> findResultByTournamentId(long tournamentId);
}
