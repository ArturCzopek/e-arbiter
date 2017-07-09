package pl.cyganki.gateway.service

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

    private var user: User? = null

    fun getLoggedInUser(): User? {

        // we don't store user there, so we need to fetch all data from auth module
        if (user == null) {
            user = authModule.getUser(getLoggedInUserAuthToken())
        }

        return user
    }

    fun getNameOfCurrentLoggedInUser() = user?.name ?: "NOT LOGGED IT"

    fun clearUser() {
        this.user = null
    }

    fun isLoggedInUserSysAdmin() = user?.roles?.any { it.name == GlobalValues.SYS_ADMIN_ROLE_NAME } ?: false

    fun isLoggedInUserAdmin() = user?.roles?.any { it.name == GlobalValues.ADMIN_ROLE_NAME } ?: false
}