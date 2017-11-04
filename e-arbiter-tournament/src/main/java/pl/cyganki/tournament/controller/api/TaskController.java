package pl.cyganki.tournament.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.tournament.model.CodeSubmitForm;
import pl.cyganki.tournament.service.TaskService;
import pl.cyganki.utils.model.executor.ExecutionResult;
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
