package pl.cyganki.executor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 328978394598435L;

    private int id;
    private String name;
}
