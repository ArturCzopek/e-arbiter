package pl.cyganki.results.repository;


import org.springframework.data.repository.CrudRepository;
import pl.cyganki.results.model.database.Result;

import java.util.List;

public interface ResultRepository extends CrudRepository<Result, Long> {

    List<Result> findResultByTournamentId(long tournamentId);

    List<Result> findResultByTournamentIdAndLanguage(long tournamentId, Result.Language language);
}
