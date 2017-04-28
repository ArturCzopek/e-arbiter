package pl.cyganki.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PocFilter extends ZuulFilter{

    @Override
    public String filterType() {
        System.out.println("tajp");
        return "pre";
    }

    @Override
    public int filterOrder() {
        System.out.println("order");
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        System.out.println("Should filter");
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        System.out.println(ctx);
        System.out.println("Run");
        HttpServletRequest request = ctx.getRequest();

        System.out.println(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        return null;
    }
}
