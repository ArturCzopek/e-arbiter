package pl.cyganki.tournament;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EArbiterTournamentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EArbiterTournamentApplication.class, args);
    }
}