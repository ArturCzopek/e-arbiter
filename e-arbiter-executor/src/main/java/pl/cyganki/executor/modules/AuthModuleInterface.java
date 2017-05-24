package pl.cyganki.executor.modules;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cyganki.executor.model.User;

@FeignClient("authentication-module")
public interface AuthModuleInterface {

    @RequestMapping("/api/user")
    User getUser(@RequestHeader("oauth_token") String token);

    @RequestMapping("/api/token")
    String getToken();
}