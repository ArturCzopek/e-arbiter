package pl.cyganki.auth.controller.inner

import io.swagger.annotations.ApiOperation
import org.springframework.cache.annotation.Cacheable
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
    @Cacheable(value = "inner-name", key = "#id")   // it doesn't have to be cleaned, name is taken from gh, it's immutable
    @ApiOperation("Returns a name of user by passed user id. Used for fetching information about tournament. There always should be user because passed id is from module where existing user's id is stored")
    fun getUserNameById(@PathVariable("id") id: Long) = userService.getUserNameById(id)
}