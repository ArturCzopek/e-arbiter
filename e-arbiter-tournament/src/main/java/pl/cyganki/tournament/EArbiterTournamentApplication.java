package pl.cyganki.tournament;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import pl.cyganki.utils.annotation.EnableArbiterSwagger;

@EnableEurekaClient
@EnableArbiterSwagger
@SpringBootApplication
public class EArbiterTournamentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EArbiterTournamentApplication.class, args);
    }
}