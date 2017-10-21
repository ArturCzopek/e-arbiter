package pl.cyganki.auth.controller.inner

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import pl.cyganki.auth.service.UserService

/**
 * Controller for fetching other users data.
 *
 * IMPORTANT!! IT MUSTN'T BE A PART OF PUBLIC API!! So, address other than "/api/..." must be used
 */
@RestController
@RequestMapping("/inner/user")
class InnerUserDataController(private val userService: UserService) {

    @GetMapping("/name/{id}")
    @ApiOperation("Returns a name of user by passed user id. There always should be user because passed id is from module where existing user's id is stored")
    fun getUserNameById(@PathVariable("id") id: Long) = userService.getUserNameById(id)

    @GetMapping("/names")
    @ApiOperation("Returns names of users by passed user ids. There always should be user because passed id is from module where existing user's id is stored")
    fun getUserNamesByIds(@RequestBody usersIds: List<Long>) = usersIds.map { userService.getUserNameById(it) }

    @GetMapping("/names/all")
    @ApiOperation("Returns names of all users")
    fun getAllUserNames() = userService.getAllUserNames()

    @GetMapping("/email/{id}")
    @ApiOperation("Returns a name of user by passed user id. There always should be user because passed id is from module where existing user's id is stored")
    fun getEmailById(@PathVariable("id") id: Long) = userService.getUserEmailById(id)

    @GetMapping("/emails")
    @ApiOperation("Returns mails of users by passed user ids. There always should be user because passed id is from module where existing user's id is stored")
    fun getEmailsByIds(@RequestBody usersIds: List<Long>) = usersIds.map { userService.getUserEmailById(it) }

    @GetMapping("/emails/all")
    @ApiOperation("Returns emails of all users")
    fun getAllUsersEmails() = userService.getAllUsersEmails()
}