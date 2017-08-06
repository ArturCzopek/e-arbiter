package pl.cyganki.tournament.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.model.TournamentStatus;

public interface TournamentRepository extends MongoRepository<Tournament, Long> {

    @Query("{ " +
                "$and: [" +
                    "{ 'joinedUsersIds': ?0 }," +
                    "{ 'status': ?1 } " +
                "]" +
            "}")
    Page<Tournament> findAllTournamentsWhereUserParticipateByStatus(long userId, TournamentStatus status, Pageable pageable);

    @Query("{ " +
                "$and: [" +
                    "{ 'joinedUsersIds': ?0 }," +
                    "{ 'status': ?1 }, " +
                    "{ " +
                        "$or: [" +
                            "{'name': /?2/ }," +
                            "{'description': /?2/ }," +
                        "]" +
                    "}" +
                "]" +
            "}")
    Page<Tournament> findAllTournamentsWhereUserParticipateByStatusAndQuery(long userId, TournamentStatus status, String query, Pageable pageable);
}

