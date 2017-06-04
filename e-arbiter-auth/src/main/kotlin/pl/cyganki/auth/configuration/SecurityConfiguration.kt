package pl.cyganki.auth.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Profile("dev")
@Configuration
@EnableOAuth2Sso
open class Security(
        @Value("\${e-arbiter.clientUrl}") var clientUrl: String,
        @Value("\${e-arbiter.proxyUrl}") var proxyUrl: String
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/index.html", "/login**", "/api/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/"))
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("$clientUrl/logout")
                .and().csrf().disable()
    }

    @Bean
    open fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurerAdapter() {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins(proxyUrl, clientUrl)
            }
        }
    }
}

