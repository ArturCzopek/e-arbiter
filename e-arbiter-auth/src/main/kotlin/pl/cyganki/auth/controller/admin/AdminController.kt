package pl.cyganki.auth.controller.admin;

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.cyganki.auth.model.DbUser
import pl.cyganki.auth.service.AuthService

@RequestMapping("/admin")
@RestController
class AdminController(private val authService: AuthService) {

    @GetMapping("/all")
    @ApiOperation("Returns all users from db. Filter returns 401 if user has no admin role what is checked by API Gateway.")
    fun getAllUsersFromDb(): List<DbUser> = authService.getAllUsersFromDb()

    @GetMapping("/ping")
    @ApiOperation("Ping for admins. Filter returns 401 if user has no admin role what is checked by API Gateway.")
    fun pingForAdmin() = "ok"
}
