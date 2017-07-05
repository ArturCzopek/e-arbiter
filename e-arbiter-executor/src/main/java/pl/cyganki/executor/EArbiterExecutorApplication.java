package pl.cyganki.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import pl.cyganki.utils.modules.EnableArbiterModules;
import pl.cyganki.utils.swagger.EnableArbiterSwagger;

@SpringBootApplication
@EnableEurekaClient
@EnableArbiterModules
@EnableArbiterSwagger
@EnableCircuitBreaker
public class EArbiterExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EArbiterExecutorApplication.class, args);
	}

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
