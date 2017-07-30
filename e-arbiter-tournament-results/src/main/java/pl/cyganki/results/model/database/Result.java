package pl.cyganki.results.model.database;

import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "result_type")
public abstract class Result {

    @Id
    @Column(name = "result_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "tournament_id")
    private String tournamentId;

    @Column(name = "earned_points")
    private String earnedPoints;

    public long getEarnedPoints() {
        return Arrays.stream(this.earnedPoints.split(","))
                .mapToLong(Long::parseLong)
                .sum();
    }

    public List<Long> getListOfEarnedPoints() {
        return Arrays.stream(this.earnedPoints.split(","))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toList());
    }
}
