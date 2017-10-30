package pl.cyganki.gateway.service

import mu.KLogging
import org.springframework.stereotype.Service
import pl.cyganki.gateway.utils.getLoggedInUserAuthToken
import pl.cyganki.utils.GlobalValues
import pl.cyganki.utils.modules.AuthModuleInterface
import pl.cyganki.utils.security.dto.User


/**
 * Class responsible for storing logged in user per session
 * and handling his state on the gateway level.
 * This class should reduce amount of request to other modules (especially to auth module)
 */
@Service
class UserSessionCache(val authModule: AuthModuleInterface) {

    private val users: MutableMap<String, User> = hashMapOf()   // this map stores current logged in user. User is identified by token

    fun getLoggedInUser(): User? {

        val token = getLoggedInUserAuthToken()

        // we don't store user there, so we need to fetch all data from auth module
        if (users[token] == null) {
            try {
                val user = authModule.getUser(token)
                setUser(token, user)
            } catch (e: Exception) {
                logger.warn("Cannot get user with token: $token, ${e.message}")
                return null
            }
        }

        return users[token]
    }

    fun getNameOfCurrentLoggedInUser() = getUserNameByToken(getLoggedInUserAuthToken())

    fun getUserNameByToken(token: String) = users[token]?.name ?: "NOT LOGGED IT"

    fun clearUser(token: String) {
        users.remove(token)
    }

    fun setUser(token: String, user: User) {
        users[token] = user
    }

    fun isLoggedInUserAdmin() = users[getLoggedInUserAuthToken()]?.roles?.any { it.name == GlobalValues.ADMIN_ROLE_NAME } ?: false

    companion object: KLogging()
}