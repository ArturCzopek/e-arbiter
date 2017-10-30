package pl.cyganki.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import pl.cyganki.utils.annotation.EnableArbiterAdminServiceData;
import pl.cyganki.utils.annotation.EnableArbiterBasicSecurity;
import pl.cyganki.utils.annotation.EnableArbiterModules;

@EnableArbiterAdminServiceData
@EnableArbiterBasicSecurity
@EnableArbiterModules
@EnableCircuitBreaker
@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class EArbiterApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EArbiterApiGatewayApplication.class, args);
    }
}
