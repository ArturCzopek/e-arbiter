package pl.cyganki.tournament.model;

import lombok.Builder;

@Builder
public class Answer {
    private String content;
    private boolean correct;

}