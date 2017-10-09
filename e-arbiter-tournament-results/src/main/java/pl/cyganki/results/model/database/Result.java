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

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "earned_points")
    private String earnedPoints;

    public int getEarnedPoints() {
        return Arrays.stream(this.earnedPoints.split(","))
                .mapToInt(Integer::parseInt)
                .sum();
    }

    public List<Integer> getListOfEarnedPoints() {
        return Arrays.stream(this.earnedPoints.split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setEarnedPoints(String earnedPoints) {
        this.earnedPoints = earnedPoints;
    }
}
