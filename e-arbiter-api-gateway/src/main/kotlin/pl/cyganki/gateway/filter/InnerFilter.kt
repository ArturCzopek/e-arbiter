package pl.cyganki.gateway.filter

import com.netflix.zuul.ZuulFilter
import org.springframework.stereotype.Component
import pl.cyganki.gateway.filter.utils.FilterRegex
import pl.cyganki.gateway.utils.FilterType
import pl.cyganki.gateway.utils.getRequest
import java.util.regex.Pattern


@Component
class InnerFilter: ZuulFilter() {

    override fun shouldFilter() = Pattern.matches(FilterRegex.INNER_PATH, getRequest().requestURI)

    override fun filterType() = FilterType.PRE.value

    override fun filterOrder() = 2

    override fun run(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}