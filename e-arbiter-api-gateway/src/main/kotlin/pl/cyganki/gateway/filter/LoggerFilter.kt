package pl.cyganki.gateway.filter

import com.netflix.zuul.ZuulFilter
import mu.KLogging
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import pl.cyganki.gateway.service.UserSessionCache
import pl.cyganki.gateway.utils.FilterType
import pl.cyganki.gateway.utils.getRequest
import pl.cyganki.gateway.utils.getResponse


/**
 * Filter responsible for logging all requests through API Gateway (except OPTIONS type)
 */
@Component
class LoggerFilter(private val userSessionCache: UserSessionCache): ZuulFilter() {

    // do not log OPTIONS request. It is not worth of it because we don't have token there and we cannot write a user name
    override fun shouldFilter() = !RequestMethod.OPTIONS.toString().equals(getRequest().method, ignoreCase = true)

    override fun filterType() = FilterType.POST.value

    override fun filterOrder() = 2

    override fun run(): Any? {
        val userName = userSessionCache.getNameOfCurrentLoggedInUser()
        val request = getRequest()
        val response = getResponse()

        logger.info("[$userName] - REQUEST >> ${request.requestURL} (${request.method}); RESPONSE << ${response.status}")

        return null
    }

    companion object: KLogging()
}