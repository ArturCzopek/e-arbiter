package pl.cyganki.tournament;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.cyganki.utils.annotation.EnableArbiterBasicSecurity;
import pl.cyganki.utils.annotation.EnableArbiterModules;
import pl.cyganki.utils.annotation.EnableArbiterResolvers;
import pl.cyganki.utils.annotation.EnableArbiterSwagger;

@EnableArbiterBasicSecurity
@EnableArbiterModules
@EnableArbiterResolvers
@EnableArbiterSwagger
@EnableCircuitBreaker
@EnableEurekaClient
@EnableScheduling
@SpringBootApplication
public class EArbiterTournamentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EArbiterTournamentApplication.class, args);
    }
}