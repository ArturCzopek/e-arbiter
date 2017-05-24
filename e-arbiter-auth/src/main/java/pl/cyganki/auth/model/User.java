package pl.cyganki.auth.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 328978394598435L;

    private int id;
    private String name;
}
