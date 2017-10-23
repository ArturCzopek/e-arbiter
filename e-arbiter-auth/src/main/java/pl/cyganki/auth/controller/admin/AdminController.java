package pl.cyganki.auth.controller.admin;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.auth.model.DbUser;
import pl.cyganki.auth.service.AuthService;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminController {

    private AuthService authService;

    @Autowired
    public AdminController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/all")
    @ApiOperation("Returns all users from db. Filter returns 401 if user has no admin role what is checked by API Gateway.")
    public List<DbUser> getAllUsersFromDb() {
        return authService.getAllUsersFromDb();
    }

    @GetMapping("/ping")
    @ApiOperation("Ping for admins. Filter returns 401 if user has no admin role what is checked by API Gateway.")
    public String pingForAdmin() {
        return "ok";
    }
}
