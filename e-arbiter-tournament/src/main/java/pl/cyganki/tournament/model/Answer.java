package pl.cyganki.tournament.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Builder
@EqualsAndHashCode
@Data
public class Answer implements Serializable {
    private static final long serialVersionUID = 3629960358660808621L;

    private String content;
    private boolean correct;
}