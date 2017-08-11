package pl.cyganki.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pl.cyganki.utils.annotation.EnableArbiterBasicSecurity;
import pl.cyganki.utils.annotation.EnableArbiterModules;
import pl.cyganki.utils.annotation.EnableArbiterSwagger;

@EnableArbiterBasicSecurity
@EnableArbiterModules
@EnableArbiterSwagger
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class EArbiterExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EArbiterExecutorApplication.class, args);
	}

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
