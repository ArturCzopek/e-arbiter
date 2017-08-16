package pl.cyganki.executor.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Profile({"dev","prod"})
@Slf4j
public class SecurityConfiguration {

    @Value("${e-arbiter.clientUrl}")
    private String clientUrl;

    @Value("${e-arbiter.proxyUrl}")
    private String proxyUrl;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(proxyUrl, clientUrl);
                log.info("Allowed AJAX origins: {}, {}", clientUrl, proxyUrl);
            }
        };
    }
}
