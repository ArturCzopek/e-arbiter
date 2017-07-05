package pl.cyganki.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pl.cyganki.utils.swagger.EnableArbiterSwagger;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableArbiterSwagger
public class EArbiterAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(EArbiterAuthApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
