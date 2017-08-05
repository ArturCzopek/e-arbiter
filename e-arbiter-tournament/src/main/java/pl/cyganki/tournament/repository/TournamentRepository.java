package pl.cyganki.tournament.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.model.TournamentStatus;

public interface TournamentRepository extends MongoRepository<Tournament, Long> {

    // Method for getting all tournaments in which user participate which proper status: ACTIVE/FINISHED
    Page<Tournament> findByJoinedUsersIdsContainsAndStatusAndOrderByEndDateDesc(long userId, TournamentStatus status, Pageable pageable);
}

