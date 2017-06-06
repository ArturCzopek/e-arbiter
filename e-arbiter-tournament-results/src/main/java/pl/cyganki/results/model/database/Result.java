package pl.cyganki.results.model.database;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;

@Data
@Entity
@Table(name = "results")
public class Result {

    public enum Language{
        JAVA,
        PYTHON,
        C11,
        CPP
    }

    @Id
    @Column(name = "result_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long resultId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "tournament_id")
    private long tournamentId;

    @Column(name ="result_code")
    private Blob resultCode;

    @Enumerated(EnumType.STRING)
    @Column(name ="language")
    private Language language;

    @Column(name = "execution_time")
    private double executionTime;

    @Column(name = "earned_points")
    private double earnedPoints;
}