package pl.cyganki.gateway.filter


import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.zuul.ZuulFilter
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import pl.cyganki.gateway.service.UserSessionCache
import pl.cyganki.gateway.utils.*
import pl.cyganki.utils.security.dto.User


/**
 * Filter responsible to cache after sending request about getting user from server
 * It prevents duplicate request to auth module for user
 */
@Component
class GetUserFilter(val userSessionCache: UserSessionCache) : ZuulFilter() {
    override fun shouldFilter() = getRequest().requestURI.contains("/auth/api/user")
            && RequestMethod.GET.toString().equals(getRequest().method, ignoreCase = true)
            && HttpStatus.OK.value() == getResponse().status

    override fun filterType() = FilterType.POST.value

    override fun filterOrder() = 5

    override fun run(): Any? {
        val token = getLoggedInUserAuthToken()
        val convertedStreamToString = getResponseStreamAsString()
        val userFromResponse = ObjectMapper().readValue(convertedStreamToString, User::class.java)
        userSessionCache.setUser(token, userFromResponse)
        logger.info("Got user with token $token, $userFromResponse")
        setResponseBody(convertedStreamToString)   // response body must be set again after fetching stream, don't ask wy
        return null
    }

    companion object: KLogging()
}