package pl.cyganki.tournament;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import pl.cyganki.utils.annotation.EnableArbiterModules;
import pl.cyganki.utils.annotation.EnableArbiterResolvers;
import pl.cyganki.utils.annotation.EnableArbiterSwagger;

@EnableArbiterModules
@EnableArbiterResolvers
@EnableArbiterSwagger
@EnableEurekaClient
@SpringBootApplication
public class EArbiterTournamentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EArbiterTournamentApplication.class, args);
    }

}