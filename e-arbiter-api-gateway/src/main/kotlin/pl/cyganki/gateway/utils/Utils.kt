package pl.cyganki.gateway.utils

import com.google.common.io.CharStreams
import com.netflix.zuul.context.RequestContext
import org.springframework.http.HttpStatus
import pl.cyganki.utils.GlobalValues
import java.io.InputStreamReader

val STREAM_ENCODING = "UTF-8"

fun getRequest() = RequestContext.getCurrentContext().request

fun getResponse() = RequestContext.getCurrentContext().response

fun getLoggedInUserAuthToken() = getRequest().getHeader(GlobalValues.AUTH_TOKEN) ?: ""

fun getResponseStreamAsString() = CharStreams.toString(InputStreamReader(RequestContext.getCurrentContext().responseDataStream, STREAM_ENCODING))

fun setResponseBody(body: String) {
    RequestContext.getCurrentContext().responseBody = body
}

fun unauthorizeRequest() {
    RequestContext.getCurrentContext().run {
        setSendZuulResponse(false)
        responseStatusCode = HttpStatus.UNAUTHORIZED.value()
    }
}