package pl.cyganki.results.model.database;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    private long userId;

    @Column(name = "github_id", nullable = false, unique = true)
    private long githubId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "result_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Result> results = new ArrayList<>();
}
