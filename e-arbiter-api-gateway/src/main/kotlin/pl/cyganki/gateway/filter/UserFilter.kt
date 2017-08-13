package pl.cyganki.gateway.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import mu.KLogging
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import pl.cyganki.gateway.service.UserSessionCache
import pl.cyganki.gateway.utils.FilterType
import pl.cyganki.gateway.utils.getRequest
import pl.cyganki.utils.GlobalValues

/**
 * Filter responsible for adding user to almost each request in header as a JSON
 * In microservices, user is authenticated mainly on this class.
 * If you want to disable filter for specified paths, you can add it to excludedPaths list
 */
@Component
class UserFilter(val userSessionCache: UserSessionCache) : ZuulFilter() {

    val excludedPaths = listOf(
            "/logout",
            "/api/user",
            "/api/token",
            "/rest-api"
    )

    // request with OPTIONS has not proper headers so we can mustn't filter it in this way
    override fun shouldFilter() = excludedPaths.all { !getRequest().requestURI.contains(it) } && !getRequest().method.equals(RequestMethod.OPTIONS.toString(), ignoreCase = true)

    override fun filterType() = FilterType.PRE.value

    override fun filterOrder() = 0

    override fun run(): Any? {
        RequestContext.getCurrentContext().addZuulRequestHeader(GlobalValues.USER_ATTR_KEY, userSessionCache.getLoggedInUser().toString())
        return null
    }

    companion object : KLogging()
}