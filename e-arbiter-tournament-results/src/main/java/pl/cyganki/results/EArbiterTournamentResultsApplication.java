package pl.cyganki.results;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import pl.cyganki.utils.annotation.EnableArbiterBasicSecurity;
import pl.cyganki.utils.annotation.EnableArbiterResolvers;
import pl.cyganki.utils.annotation.EnableArbiterSwagger;
import pl.cyganki.utils.annotation.EnableH2Console;

@EnableArbiterBasicSecurity
@EnableArbiterResolvers
@EnableArbiterSwagger
@EnableCircuitBreaker
@EnableEurekaClient
@EnableH2Console
@SpringBootApplication
public class EArbiterTournamentResultsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EArbiterTournamentResultsApplication.class, args);
    }
}