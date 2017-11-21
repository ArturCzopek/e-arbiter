package pl.cyganki.gateway.filter

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import pl.cyganki.gateway.service.UserSessionCache
import pl.cyganki.gateway.utils.FilterType
import pl.cyganki.gateway.utils.getRequest
import pl.cyganki.gateway.utils.getResponse
import pl.cyganki.gateway.utils.getResponseStreamAsString
import pl.cyganki.utils.security.dto.User


/**
 * Filter responsible for updating user status in cache when user status has been toggled by admin
 */
@Component
class ToggleUserStatusFilter(private val userSessionCache: UserSessionCache) : ZuulFilter() {

    override fun shouldFilter() = getRequest().requestURI.contains("/auth/admin/status") || getRequest().requestURI.contains("/auth/admin/admin-role")
            && RequestMethod.PUT.toString().equals(getRequest().method, ignoreCase = true)
            && HttpStatus.OK.value() == getResponse().status

    override fun filterType() = FilterType.POST.value

    override fun filterOrder() = 10

    override fun run(): Any? {
        val convertedStreamToString = getResponseStreamAsString()
        val userFromResponse = ObjectMapper().apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }.readValue(convertedStreamToString, User::class.java)

        this.userSessionCache.toggleUserStatus(userFromResponse.name)
        this.userSessionCache.updateUserRoles(userFromResponse.name, userFromResponse.roles)
        logger.info("[${userFromResponse.name}] - toggled user activity status")
        RequestContext.getCurrentContext().responseDataStream = convertedStreamToString.byteInputStream()

        return null
    }

    companion object : KLogging()
}