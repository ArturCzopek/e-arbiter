package pl.cyganki.results.repository;

import pl.cyganki.results.model.database.Result;

import javax.transaction.Transactional;

@Transactional
public interface ResultRepository extends ResultBaseRepository<Result> {
}
