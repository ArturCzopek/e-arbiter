package pl.cyganki.gateway.filter

import com.netflix.zuul.ZuulFilter
import mu.KLogging
import org.springframework.stereotype.Component
import pl.cyganki.gateway.service.UserSessionCache
import pl.cyganki.gateway.utils.FilterType
import pl.cyganki.gateway.utils.getRequest


/**
 * Filter responsible for clean-up after user logout
 */
@Component
class LogoutFilter(val userSessionCache: UserSessionCache) : ZuulFilter() {

    override fun shouldFilter() = getRequest().requestURI.contains("/logout")

    override fun filterType() = FilterType.PRE.value

    override fun filterOrder() = 4

    override fun run(): Any? {
        val userNameBeforeLogout = userSessionCache.getNameOfCurrentLoggedInUser()
        userSessionCache.clearUser()
        logger.info("[$userNameBeforeLogout] - logged out")
        return null
    }

    companion object : KLogging()
}