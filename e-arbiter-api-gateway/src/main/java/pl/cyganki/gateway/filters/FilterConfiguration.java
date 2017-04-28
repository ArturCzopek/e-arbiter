package pl.cyganki.gateway.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public PocFilter pocFilter() {
        return new PocFilter();
    }
}
