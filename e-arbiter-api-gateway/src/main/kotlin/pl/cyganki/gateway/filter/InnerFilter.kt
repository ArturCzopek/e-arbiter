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


@Component
class InnerFilter(val userSessionCache: UserSessionCache): ZuulFilter() {

    override fun shouldFilter() = Pattern.matches(FilterRegex.INNER_PATH, getRequest().requestURI)
            && !getRequest().method.equals(RequestMethod.OPTIONS.toString(), ignoreCase = true)

    override fun filterType() = FilterType.PRE.value

    override fun filterOrder() = 2

    override fun run(): Any? {
        val userName = userSessionCache.getNameOfCurrentLoggedInUser()

        logger.warn("[$userName] - Tried to use inner path!!")
        unauthorizeRequest()
        return null
    }

    companion object: KLogging()
}