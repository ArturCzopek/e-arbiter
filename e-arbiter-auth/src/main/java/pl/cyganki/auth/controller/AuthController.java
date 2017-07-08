package pl.cyganki.auth.controller;

import io.swagger.annotations.ApiOperation;
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
import pl.cyganki.auth.service.UserService;
import pl.cyganki.utils.GlobalValues;
import pl.cyganki.utils.security.dto.User;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private RestTemplate restTemplate;
    private UserService userService;

    @Autowired
    public AuthController(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    @GetMapping("/me")
    @ApiOperation("Returns a current logged in user based on object from request from API Gateway")
    public User getUserFromRequest(User user) throws IOException {
        return user;
    }

    @GetMapping("/user")
    @ApiOperation("Returns a current logged in user based on passed token. If user does not exist, then is created.")
    public User getUser(@RequestHeader(GlobalValues.AUTH_TOKEN) String token) {
        Map<String, Object> userMap = restTemplate.getForObject("https://api.github.com/user?access_token=" + token, Map.class);
        return userService.getLoggedInUser(userMap);
    }

    @GetMapping("/token")
    @ApiOperation("Returns a token for current logged in user. Token is widely used in app to authenticate user.")
    public ResponseEntity<String> getToken() {

        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        try {
            return ResponseEntity.ok(
                    ((OAuth2AuthenticationDetails) principal.getDetails()).getTokenValue());
        } catch (ClassCastException | NullPointerException ex) {
            // no user or anonymous user, return forbidden status
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}