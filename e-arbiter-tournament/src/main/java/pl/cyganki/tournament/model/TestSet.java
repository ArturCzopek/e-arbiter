package pl.cyganki.tournament.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class TestSet {

    @NotNull(message = "TestSet's 'expectedResult' cannot be empty")
    @Size(min = 3, max = 64, message = "TestSet's 'expectedResult' must be of length between 3 and 64 characters")
    private String expectedResult;

    @NotNull(message = "TestSet's parameter list cannot be empty")
    @Size(min = 1, message = "TestSet must contain at least one parameter")
    private List<String> parameters;
}