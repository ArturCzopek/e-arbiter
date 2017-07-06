package pl.cyganki.gateway.utils

import com.netflix.zuul.context.RequestContext

fun getLoggedInUserAuthToken() = RequestContext.getCurrentContext().request.getHeader("oauth_token")!!