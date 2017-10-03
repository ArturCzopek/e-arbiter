package pl.cyganki.auth.configuration.dev

import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint

@Profile("dev")
@Configuration
@EnableOAuth2Sso
class SecurityConfiguration(
        @Value("\${e-arbiter.clientUrl}") private var clientUrl: String,
        @Value("\${spring.h2.console.path}") private var h2ConsoleUrl: String,
        @Value("\${e-arbiter.swagger.path}") private var swaggerUrl: String     // we need to precede it later by "/" because address to it by prop is without "/"
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/index.html", "/login**", "/api/**", "/inner/**", h2ConsoleUrl, "/$swaggerUrl", "/swagger-ui.html", "/hystrix.stream**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/"))
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("$clientUrl/#/logout")
                .invalidateHttpSession(true)
                .and().csrf().disable().headers().frameOptions().disable()

        logger.info("Configured dev security")
    }

    companion object: KLogging()
}

