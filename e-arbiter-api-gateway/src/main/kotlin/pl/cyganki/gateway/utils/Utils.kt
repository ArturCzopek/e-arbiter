package pl.cyganki.gateway.utils

import com.netflix.zuul.context.RequestContext
import org.springframework.http.HttpStatus
import pl.cyganki.utils.GlobalValues

fun getRequest() = RequestContext.getCurrentContext().request

fun getResponse() = RequestContext.getCurrentContext().response

fun getLoggedInUserAuthToken() = getRequest().getHeader(GlobalValues.AUTH_TOKEN) ?: ""

fun unauthorizeRequest() {
    RequestContext.getCurrentContext().run {
        setSendZuulResponse(false)
        responseStatusCode = HttpStatus.UNAUTHORIZED.value()
    }
}