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
    Page<Tournament> findAllTournamentsInWhichUserParticipatesByStatus(long userId, TournamentStatus status, Pageable pageable);

    // $options: 'i' means case insensitive
    @Query("{ " +
                "$and: [" +
                    "{ 'joinedUsersIds': ?0 }," +
                    "{ 'status': ?1 }, " +
                    "{ " +
                        "$or: [" +
                            "{'name': {$regex: ?2, $options: 'i'} }," +
                            "{'description': {$regex: ?2, $options: 'i'} }" +
                        "]" +
                    "}" +
                "]" +
            "}")
    Page<Tournament> findAllTournamentsInWhichUserParticipatesByStatusAndQuery(long userId, TournamentStatus status, String query, Pageable pageable);
}

