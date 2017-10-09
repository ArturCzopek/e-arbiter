package pl.cyganki.results.model.database;

import lombok.Data;
import pl.cyganki.utils.model.Language;

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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Blob getResultCode() {
        return resultCode;
    }

    public void setResultCode(Blob resultCode) {
        this.resultCode = resultCode;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
