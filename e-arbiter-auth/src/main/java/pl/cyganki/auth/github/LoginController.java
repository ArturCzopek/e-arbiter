package pl.cyganki.auth.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.cyganki.auth.model.User;

import java.util.Map;

@RestController()
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/user")
    public User user(@RequestParam("token") String token) {
        Map<String, Object> userMap = restTemplate.getForObject("https://api.github.com/user?access_token=" + token, Map.class);
        System.out.println("User " + userMap.get("login"));

        return new User((int) userMap.get("id"), (String) userMap.get("login"));
    }

    @RequestMapping("/token")
    public String getToken() {

        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        try {
            return ((OAuth2AuthenticationDetails) principal.getDetails()).getTokenValue();
        } catch (ClassCastException | NullPointerException ex) {
            // no user or anonymous user, return null
            return null;
        }
    }

    @RequestMapping("/clientUrl")
    public String getClientUrl() {
        return "http://localhost:4200";
    }

}