package pl.cyganki.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.cyganki.auth.model.User;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/user")
    public User user(@RequestHeader("oauth_token") String token) {
        Map<String, Object> userMap = restTemplate.getForObject("https://api.github.com/user?access_token=" + token, Map.class);
        return new User((int) userMap.get("id"), (String) userMap.get("login"));
    }

    @GetMapping("/token")
    public ResponseEntity<String> getToken() {

        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        try {
            System.out.println("println2");

            return ResponseEntity.ok(
                    ((OAuth2AuthenticationDetails) principal.getDetails()).getTokenValue());
        } catch (ClassCastException | NullPointerException ex) {
            // no user or anonymous user, return forbidden status
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}