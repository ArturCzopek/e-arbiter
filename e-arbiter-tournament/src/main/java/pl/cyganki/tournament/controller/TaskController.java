package pl.cyganki.tournament.controller;

import org.springframework.web.bind.annotation.*;
import pl.cyganki.tournament.service.TaskService;
import pl.cyganki.utils.modules.executor.model.ExecutionResult;
import pl.cyganki.utils.modules.tournament.model.Language;
import pl.cyganki.utils.security.dto.User;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/submit")
    public ExecutionResult submitSolution(User user, @RequestBody Map<String, String> data) {
        return taskService.submitCode(data.get("tournamentId"), data.get("taskId"), data.get("program").getBytes(), Language.C11);
    }
}
