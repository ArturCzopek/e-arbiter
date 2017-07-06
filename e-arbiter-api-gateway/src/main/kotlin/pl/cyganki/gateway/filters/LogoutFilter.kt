package pl.cyganki.gateway.filters

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import mu.KLogging
import org.springframework.stereotype.Component
import pl.cyganki.gateway.utils.FilterType


/**
 * Filter will be developed soon
 * It's in case when user is logged out and caches are not cleared from user
 */
@Component
class LogoutFilter : ZuulFilter() {

    override fun shouldFilter(): Boolean {
        val path = RequestContext.getCurrentContext().request.requestURI
        return path.contains("/logout")
    }

    override fun filterType() = FilterType.POST.value

    override fun filterOrder() = 0

    override fun run(): Any? {
        logger.info("Logged out user")
        return null
    }

    companion object : KLogging()
}