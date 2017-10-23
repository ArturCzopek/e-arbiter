package pl.cyganki.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import pl.cyganki.utils.annotation.EnableArbiterAdminServiceData;
import pl.cyganki.utils.annotation.EnableArbiterBasicSecurity;

@EnableArbiterAdminServiceData
@EnableArbiterBasicSecurity
@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication
public class EArbiterConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(EArbiterConfigApplication.class, args);
    }
}