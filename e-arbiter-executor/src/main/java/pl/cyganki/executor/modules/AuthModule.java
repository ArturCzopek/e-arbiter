package pl.cyganki.executor.modules;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("authentication-module")
public interface AuthModule {

    @RequestMapping("/login")
    String login();
}