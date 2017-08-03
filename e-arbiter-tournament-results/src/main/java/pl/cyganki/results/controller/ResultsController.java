package pl.cyganki.results.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.results.model.database.Result;
import pl.cyganki.results.repository.ResultRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ResultsController {

    private ResultRepository resultRepository;

    @Autowired
    public ResultsController(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @GetMapping("/all")
    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }
}
