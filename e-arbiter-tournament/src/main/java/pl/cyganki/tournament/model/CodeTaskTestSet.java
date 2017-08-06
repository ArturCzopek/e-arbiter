package pl.cyganki.tournament.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class CodeTaskTestSet {

    @NotNull(message = "CodeTaskTestSet's 'expectedResult' cannot be empty")
    @Size(min = 1, max = 64, message = "CodeTaskTestSet's 'expectedResult' must be of length between 1 and 64 characters")
    private String expectedResult;

    @NotNull(message = "CodeTaskTestSet's parameter list cannot be empty")
    @Size(min = 1, message = "CodeTaskTestSet must contain at least one parameter")
    private List<String> parameters;
}