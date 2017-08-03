package pl.cyganki.tournament.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CodeTaskTestSet {

    public CodeTaskTestSet() {
        this.id = ObjectId.get().toString();
    }

    @Id
    private String id;

    @NotNull(message = "CodeTaskTestSet's 'expectedResult' cannot be empty")
    @Size(min = 3, max = 64, message = "CodeTaskTestSet's 'expectedResult' must be of length between 3 and 64 characters")
    private String expectedResult;

    @NotNull(message = "CodeTaskTestSet's parameter list cannot be empty")
    @Size(min = 1, message = "CodeTaskTestSet must contain at least one parameter")
    private List<String> parameters;
}