package pl.cyganki.gateway.filter

import com.netflix.zuul.ZuulFilter
import mu.KLogging
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import pl.cyganki.gateway.filter.utils.FilterRegex
import pl.cyganki.gateway.service.UserSessionCache
import pl.cyganki.gateway.utils.FilterType
import pl.cyganki.gateway.utils.getRequest
import pl.cyganki.gateway.utils.unauthorizeRequest
import java.util.regex.Pattern

/**
 * Filter responsible for checking if user is a SysAdmin
 * If not, this event is written to logs and request is invalid
 */
@Component
class SysAdminFilter(private val userSessionCache: UserSessionCache) : ZuulFilter() {

    override fun shouldFilter() = Pattern.matches(FilterRegex.SYS_ADMIN_PATH, getRequest().requestURI)
            && !getRequest().method.equals(RequestMethod.OPTIONS.toString(), ignoreCase = true)

    override fun filterType() = FilterType.PRE.value

    override fun filterOrder() = 3

    override fun run(): Any? {

        val userName = userSessionCache.getNameOfCurrentLoggedInUser()

        if (userSessionCache.isLoggedInUserSysAdmin()) {
            logger.info("[$userName] - has a sysadmin role")
        } else {
            // proper path will be printed by LoggerFilter.kt
            logger.warn("[$userName] - tried to get by sysadmin path!")
            unauthorizeRequest()
        }

        return null
    }

    companion object : KLogging()
}