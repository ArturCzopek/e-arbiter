package pl.cyganki.gateway.utils

import com.netflix.zuul.context.RequestContext
import org.springframework.http.HttpStatus

fun getRequest() = RequestContext.getCurrentContext().request

fun getResponse() = RequestContext.getCurrentContext().response

fun getLoggedInUserAuthToken() = getRequest().getHeader("oauth_token") ?: ""

fun unauthorizeRequest() {
    RequestContext.getCurrentContext().run {
        setSendZuulResponse(false)
        responseStatusCode = HttpStatus.UNAUTHORIZED.value()
    }
}