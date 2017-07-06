package pl.cyganki.gateway.filter

import com.netflix.zuul.ZuulFilter
import mu.KLogging
import org.springframework.stereotype.Component
import pl.cyganki.gateway.service.UserSessionCache
import pl.cyganki.gateway.utils.FilterType
import pl.cyganki.gateway.utils.getRequest

/**
 * Filter responsible for adding user to each request
 * In microservices, user is authenticated mainly on this class
 */

@Component
class UserFilter(val userSessionCache: UserSessionCache) : ZuulFilter() {

    // always, except log out
    override fun shouldFilter() = !getRequest().requestURI.contains("/logout")

    override fun filterType() = FilterType.PRE.value

    override fun filterOrder() = 0

    override fun run(): Any? {
        val request = getRequest()
        request.setAttribute("user", userSessionCache.getLoggedInUser())

        return null
    }

    companion object : KLogging()
}