package pl.cyganki.results.model.database;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    private long userId;

    @Column(name = "some_content", length = 255)
    private String someContent;
}
