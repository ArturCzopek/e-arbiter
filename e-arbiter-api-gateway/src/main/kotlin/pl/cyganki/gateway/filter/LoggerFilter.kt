package pl.cyganki.gateway.filter

import com.netflix.zuul.ZuulFilter
import mu.KLogging
import org.springframework.stereotype.Component
import pl.cyganki.gateway.service.UserSessionCache
import pl.cyganki.gateway.utils.FilterType
import pl.cyganki.gateway.utils.getRequest
import pl.cyganki.gateway.utils.getResponse

/**
 * Filter responsible for logging whole request through API Gateway
 */

@Component
class LoggerFilter(val userSessionCache: UserSessionCache): ZuulFilter() {

    override fun shouldFilter() = true

    override fun filterType() = FilterType.POST.value

    override fun filterOrder() = 0

    override fun run(): Any? {
        val userName = userSessionCache.getLoggedInUser()!!.name
        val request = getRequest()
        val response = getResponse()

        logger.info("[$userName] - REQUEST >> ${request.requestURL} (${request.method}); RESPONSE << ${response.status}")

        return null
    }

    companion object: KLogging()
}