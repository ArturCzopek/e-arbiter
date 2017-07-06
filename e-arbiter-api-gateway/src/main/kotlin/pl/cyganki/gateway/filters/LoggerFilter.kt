package pl.cyganki.gateway.filters

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import mu.KLogging
import org.springframework.stereotype.Component
import pl.cyganki.gateway.utils.FilterType

@Component
class LoggerFilter: ZuulFilter() {

    override fun shouldFilter() = true

    override fun filterType() = FilterType.POST.value

    override fun filterOrder() = 0

    override fun run(): Any? {
        val userName = "testUserName"// TODO, add fetching user
        val request = RequestContext.getCurrentContext().request
        val response = RequestContext.getCurrentContext().response

        logger.info("[$userName] - REQUEST >> ${request.requestURL} (${request.method}); RESPONSE << ${response.status}")

        return null
    }

    companion object: KLogging()
}