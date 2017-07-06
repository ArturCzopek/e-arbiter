package pl.cyganki.auth.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.auth.database.entity.DbUser;
import pl.cyganki.auth.service.UserService;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    @ApiOperation("Returns all users from db. Filter returns 401 if user has no admin role.")
    public List<DbUser> getAllUsersFromDb() {
        return userService.getAllUsersFromDb();
    }

    @GetMapping("/ping")
    @ApiOperation("Ping for admins. Filter returns 401 if user has no admin role.")
    public String pingForAdmin() {
        return "ok";
    }
}
