package pl.cyganki.gateway.filter

import com.netflix.zuul.ZuulFilter
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import pl.cyganki.gateway.filter.utils.FilterRegex
import org.springframework.web.bind.annotation.RequestMethod
import pl.cyganki.gateway.service.UserSessionCache
import pl.cyganki.gateway.utils.FilterType
import pl.cyganki.gateway.utils.getLoggedInUserAuthToken
import pl.cyganki.gateway.utils.getRequest
import java.util.regex.Pattern
import pl.cyganki.gateway.utils.getResponse


/**
 * Filter responsible for clean-up after user logout
 */
@Component
class LogoutFilter(val userSessionCache: UserSessionCache) : ZuulFilter() {

    override fun shouldFilter() = Pattern.matches(FilterRegex.AUTH_LOGOUT_PATH, getRequest().requestURI)
            && RequestMethod.POST.toString().equals(getRequest().method, ignoreCase = true)
            && HttpStatus.OK.value() == getResponse().status

    override fun filterType() = FilterType.POST.value

    override fun filterOrder() = 4

    override fun run(): Any? {
        val token = getLoggedInUserAuthToken()
        val userNameBeforeLogout = userSessionCache.getUserNameByToken(token)
        userSessionCache.clearUser(token)
        logger.info("[$userNameBeforeLogout] - logged out")
        return null
    }

    companion object : KLogging()
}