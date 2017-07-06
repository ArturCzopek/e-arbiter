package pl.cyganki.gateway.service

import org.springframework.stereotype.Service
import pl.cyganki.gateway.utils.getLoggedInUserAuthToken
import pl.cyganki.utils.modules.AuthModuleInterface
import pl.cyganki.utils.security.User

/**
 * Class responsible for storing logged in user per session
 * and handling his state on the gateway level.
 * This class should reduce amount of request to other modules
 */

@Service
class UserSessionCache(val authModule: AuthModuleInterface) {

    private var user: User? = null

     fun getLoggedInUser(): User? {
        if (user == null) {
            user = authModule.getUser(getLoggedInUserAuthToken())
        }

        return user
    }

    fun clearUser() {
        this.user = null
    }

    fun isLoggedInUserSysAdmin(): Boolean {
        return user?.role?.name == "SysAdmin"
    }

    fun isLoggedInUserAdmin(): Boolean {
        return user?.role?.name == "Admin"
    }
}