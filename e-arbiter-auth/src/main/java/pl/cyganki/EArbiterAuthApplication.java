package pl.cyganki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EArbiterAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(EArbiterAuthApplication.class, args);
	}
}
