package pl.cyganki.results.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cyganki.results.model.database.Result;

import java.util.List;

public interface ResultRepository extends CrudRepository<Result, Long> {
    public List<Result> findResultByTournamentId(long tournamentId);

    public List<Result> findResultByTournamentIdAndLanguage(long tournamentId, Result.Language language);
}
