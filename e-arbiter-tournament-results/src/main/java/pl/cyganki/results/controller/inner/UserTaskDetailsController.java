package pl.cyganki.results.controller.inner;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.results.model.database.Result;
import pl.cyganki.results.repository.ResultRepository;
import pl.cyganki.utils.model.TaskUserDetails;

import java.util.List;

@RestController
@RequestMapping("/inner/details")
public class UserTaskDetailsController {

    private ResultRepository resultRepository;

    @Autowired
    public UserTaskDetailsController(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @GetMapping(value = {"/", ""})
    @ApiOperation("Returns data for user about his progress with task by passed id")
    public TaskUserDetails getTaskUserDetails(@RequestParam("taskId") String taskId, @RequestParam("userId") Long userId) {
        List<Result> userResultsForTask = resultRepository.findAllByTaskIdAndUserId(taskId, userId);
    }
}
