package pl.cyganki.gateway.filters

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import pl.cyganki.gateway.utils.FilterType


@Component
class AdminFilter : ZuulFilter() {

    override fun shouldFilter(): Boolean {
        val path = RequestContext.getCurrentContext().request.requestURI
        return path.contains("/admin")
    }

    override fun filterType() = FilterType.PRE.value

    override fun filterOrder() = 0

    override fun run(): Any? {

        // TODO check admin better...:)
        val isAdmin = false

        if (isAdmin) {
            logger.info("[testUserName] - has an admin role")
        } else {
            // proper path will be printed by LoggerFilter.kt
            logger.warn("[testUserName] - tried to get by admin path!")

            RequestContext.getCurrentContext().run {
                setSendZuulResponse(false)
                responseStatusCode = HttpStatus.FORBIDDEN.value()
            }
        }

        return null
    }

    companion object : KLogging()
}