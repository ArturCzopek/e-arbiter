package pl.cyganki.gateway.filter

import com.netflix.zuul.ZuulFilter
import mu.KLogging
import org.springframework.stereotype.Component
import pl.cyganki.gateway.service.UserSessionCache
import pl.cyganki.gateway.utils.FilterType
import pl.cyganki.gateway.utils.getRequest
import pl.cyganki.gateway.utils.unauthorizeRequest


@Component
class AdminFilter(val userSessionCache: UserSessionCache) : ZuulFilter() {

    override fun shouldFilter() = getRequest().requestURI.contains("/admin")

    override fun filterType() = FilterType.PRE.value

    override fun filterOrder() = 1

    override fun run(): Any? {

        val userName = userSessionCache.getLoggedInUser()?.name ?: ""

        if (userSessionCache.isLoggedInUserAdmin()) {
            logger.info("[$userName] - has an admin role")
        } else {
            // proper path will be printed by LoggerFilter.kt
            logger.warn("[$userName] - tried to get by admin path!")
            unauthorizeRequest()
        }

        return null
    }

    companion object : KLogging()
}