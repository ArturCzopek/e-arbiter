package pl.cyganki.tournament.model;


import lombok.Builder;

import java.util.List;

@Builder
public class TestSet {
    private String result;
    private List<String> parameters;
}