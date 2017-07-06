package pl.cyganki.gateway.filter

import com.netflix.zuul.ZuulFilter
import mu.KLogging
import org.springframework.stereotype.Component
import pl.cyganki.gateway.service.UserSessionCache
import pl.cyganki.gateway.utils.FilterType
import pl.cyganki.gateway.utils.getRequest
import pl.cyganki.gateway.utils.unauthorizeRequest

@Component
class SysAdminFilter(val userSessionCache: UserSessionCache) : ZuulFilter() {

    override fun shouldFilter() = getRequest().requestURI.contains("/sysadmin")

    override fun filterType() = FilterType.PRE.value

    override fun filterOrder() = 2

    override fun run(): Any? {

        val userName = userSessionCache.getLoggedInUser()?.name ?: ""

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