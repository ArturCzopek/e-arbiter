package pl.cyganki.tournament.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.model.TournamentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface TournamentRepository extends MongoRepository<Tournament, String> {

    // @formatter:off
    @Query("{" +
                "$and: [" +
                    "{ 'ownerId': { $ne: ?0 } }," +
                    "{ 'joinedUsersIds': ?0 }," +
                    "{ 'status': ?1 }" +
                "]" +
            "}")
    Page<Tournament> findTournamentsInWhichUserParticipatesByStatus(long userId, TournamentStatus status, Pageable pageable);

    // $options: 'i' means case insensitive
    @Query("{" +
                "$and: [" +
                    "{ 'ownerId': { $ne: ?0 } }," +
                    "{ 'joinedUsersIds': ?0 }," +
                    "{ 'status': ?1 }," +
                    "{ " +
                        "$or: [" +
                            "{ 'name': {$regex: ?2, $options: 'i'} }," +
                            "{ 'description': {$regex: ?2, $options: 'i'} }" +
                        "]" +
                    "}" +
                "]" +
            "}")
    Page<Tournament> findTournamentsInWhichUserParticipatesByStatusAndQuery(long userId, TournamentStatus status, String query, Pageable pageable);


    @Query("{" +
                "$and: [" +
                    "{ 'joinedUsersIds': { $ne: ?0 } }," +  // user is not in tournament
                    "{ 'ownerId': { $ne: ?0 } }," +         // also, user is not an owner
                    "{ 'status': 'ACTIVE' }," +             // tournament must be active
                    "{ 'publicFlag': true }" +              // and public
                "]" +
            "}")
    Page<Tournament> findPublicActiveTournamentsInWhichUserDoesNotParticipate(long userId, Pageable pageable);


    /**
     * Difference between these two methods is that we don't use pageable there. We have to take care of sorting/amount by ourselves
     * @param userId
     * @return all tournaments in which user doesn't participate and are public and active
     */
    @Query("{" +
                "$and: [" +
                    "{ 'joinedUsersIds': { $ne: ?0 } }," +  // user is not in tournament
                    "{ 'ownerId': { $ne: ?0 } }," +         // also, user is not an owner
                    "{ 'status': 'ACTIVE' }," +             // tournament must be active
                    "{ 'publicFlag': true }" +              // and public
                "]" +
            "}")
    List<Tournament> findAllPublicActiveTournamentsInWhichUserDoesNotParticipate(long userId);

    @Query("{" +
                "$and: [" +
                    "{ 'ownerId': ?0 }," +
                    "{ 'status': ?1 }" +
                "]" +
            "}")
    Page<Tournament> findTournamentsCreatedByUserByStatus(long userId, TournamentStatus status, Pageable pageable);

    @Query("{" +
                "$and: [" +
                    "{ 'ownerId': ?0 }," +
                    "{ 'status': ?1 }," +
                    "{ " +
                        "$or: [" +
                            "{ 'name': { $regex: ?2, $options: 'i' } }," +
                            "{ 'description': { $regex: ?2, $options: 'i' } }" +
                        "]" +
                    "}" +
                "]" +
            "}")
    Page<Tournament> findTournamentsCreatedByUserByStatusAndQuery(long userId, TournamentStatus status, String query, Pageable pageable);

    @Query("{" +
                "$and: [" +
                    "{ 'status': 'ACTIVE' }," +
                    "{ 'endDate': { $lt: ?0 } }" +
                "]" +
            "}")
    List<Tournament> findActiveTournamentsWhereEndDateIsEarlierThan(LocalDateTime endDate);

    @Query("{" +
                "$and: [" +
                    "{ 'id': ?0 }," +
                    "{ 'status': 'ACTIVE' }," +
                    "{ 'joinedUsersIds': ?1 }" +
                "]" +
            "}")
    Tournament findActiveTournamentInWhichUserParticipatesById(String tournamentId, Long userId);
    // @formatter:on
}

