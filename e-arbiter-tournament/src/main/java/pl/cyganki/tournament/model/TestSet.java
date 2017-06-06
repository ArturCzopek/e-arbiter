package pl.cyganki.tournament.model;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class TestSet implements Serializable {
    private static final long serialVersionUID = -5656310711805870043L;

    private String expectedResult;
    private List<String> parameters;
}