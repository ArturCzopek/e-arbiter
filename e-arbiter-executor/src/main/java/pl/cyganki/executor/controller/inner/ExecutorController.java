package pl.cyganki.executor.controller.inner;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.executor.code.docker.DockerCodeRunner;
import pl.cyganki.utils.modules.executor.model.ExecutionRequest;
import pl.cyganki.utils.modules.executor.model.ExecutionResult;

@RestController
@RequestMapping("/inner")
public class ExecutorController {

    private final DockerCodeRunner codeRunner;

    @Autowired
    public ExecutorController(DockerCodeRunner codeRunner) {
        this.codeRunner = codeRunner;
    }

    @PostMapping("/execute")
    @ApiOperation("Executes given code with respect to passed test data")
    public ExecutionResult execute(@RequestBody ExecutionRequest executionRequest) {
        return this.codeRunner.execute(executionRequest);
    }
}
