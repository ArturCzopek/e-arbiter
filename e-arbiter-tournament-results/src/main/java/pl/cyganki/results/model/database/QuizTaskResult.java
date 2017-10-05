package pl.cyganki.results.model.database;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue(value = "QUIZ")
public class QuizTaskResult extends Result {

}
