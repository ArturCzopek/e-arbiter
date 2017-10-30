package pl.cyganki.auth.controller.admin;

import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.cyganki.auth.model.DbUser
import pl.cyganki.auth.service.AuthService
import pl.cyganki.auth.service.UserService

@RequestMapping("/admin")
@RestController
class AdminController(
        private val authService: AuthService,
        private val userService: UserService
) {

    @GetMapping("/all")
    @ApiOperation("Returns all users from db. Filter returns 401 if user has no admin role what is checked by API Gateway.")
    fun getAllUsersFromDb(): List<DbUser> = authService.getAllUsersFromDb()

    @GetMapping("/ping")
    @ApiOperation("Ping for admins. Filter returns 401 if user has no admin role what is checked by API Gateway.")
    fun pingForAdmin() = "ok"

    @PutMapping("/admin-role/{id}")
    @ApiOperation("Toggle user admin role")
    fun toggleAdminRole(@PathVariable("id") userId: Long): ResponseEntity<DbUser> = ResponseEntity.ok(this.userService.toggleAdminRole(userId))

    @PutMapping("/status/{id}")
    @ApiOperation("Toggle user enablestatus")
    fun toggleStatus(@PathVariable("id") userId: Long): ResponseEntity<DbUser> = ResponseEntity.ok(this.userService.toggleStatus(userId))
}
