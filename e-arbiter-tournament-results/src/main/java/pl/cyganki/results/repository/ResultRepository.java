package pl.cyganki.results.repository;

import pl.cyganki.results.model.database.Result;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ResultRepository extends ResultBaseRepository<Result> {

    List<Result> findAllByTournamentIdAndTaskIdAndUserId(String tournamentId, String taskId, long userId);
}
