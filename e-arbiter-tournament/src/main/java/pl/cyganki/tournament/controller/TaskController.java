package pl.cyganki.tournament.controller;

import org.springframework.web.bind.annotation.*;
import pl.cyganki.tournament.model.CodeSubmitForm;
import pl.cyganki.tournament.service.TaskService;
import pl.cyganki.utils.modules.executor.model.ExecutionResult;
import pl.cyganki.utils.security.dto.User;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/submit")
    public ExecutionResult submitCode(User user, @RequestBody @Valid CodeSubmitForm codeSubmitForm) {
        return taskService.submitCode(user.getId(), codeSubmitForm);
    }
}
