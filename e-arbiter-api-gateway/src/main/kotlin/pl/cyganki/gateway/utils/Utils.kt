package pl.cyganki.gateway.utils

import com.google.common.io.CharStreams
import com.netflix.zuul.context.RequestContext
import org.springframework.http.HttpStatus
import pl.cyganki.utils.GlobalValues
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.InputStreamReader

val STREAM_ENCODING = "UTF-8"

fun getRequest(): HttpServletRequest = RequestContext.getCurrentContext().request

fun getResponse(): HttpServletResponse = RequestContext.getCurrentContext().response

fun getLoggedInUserAuthToken() = getRequest().getHeader(GlobalValues.AUTH_TOKEN) ?: ""

fun getResponseStreamAsString() = CharStreams.toString(InputStreamReader(RequestContext.getCurrentContext().responseDataStream, STREAM_ENCODING))

fun setResponseBody(body: String) {
    RequestContext.getCurrentContext().responseBody = body
}

fun unauthorizeRequest() {
    RequestContext.getCurrentContext().run {
        setSendZuulResponse(false)
        responseStatusCode = HttpStatus.NOT_FOUND.value() // let's user think that there is nothing like that endpoint
    }
}