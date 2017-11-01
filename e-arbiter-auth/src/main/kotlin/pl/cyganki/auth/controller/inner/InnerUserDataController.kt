package pl.cyganki.auth.controller.inner

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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

    @GetMapping("/names-emails/{user-ids}")
    @ApiOperation("Returns names with emails of users by passed user ids. There always should be user because passed id is from module where existing user's id is stored")
    fun getUserNamesAndEmailsByIds(@PathVariable("user-ids") usersIds: Array<Long>) = usersIds.map { userService.getUserNameAndEmailById(it) }

    @GetMapping("/names-emails/all")
    @ApiOperation("Returns names with emails of all users")
    fun getAllUserNamesAndEmails() = userService.getAllUserNamesAndEmails()
}