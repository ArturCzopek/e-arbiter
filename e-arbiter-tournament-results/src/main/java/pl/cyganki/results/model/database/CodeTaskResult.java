package pl.cyganki.results.model.database;

import lombok.Data;
import pl.cyganki.utils.modules.tournament.model.Language;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.sql.Blob;

@Data
@Entity
@DiscriminatorValue(value = "CODE")
public class CodeTaskResult extends Result {

    @Column(name ="language")
    private Language language;

    @Column(name = "result_code")
    private Blob resultCode;

    @Column(name = "execution_time")
    private long executionTime;
}
