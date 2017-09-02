package pl.cyganki.auth.controller.api;

import io.swagger.annotations.ApiOperation;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.cyganki.auth.service.AuthService;
import pl.cyganki.utils.GlobalValues;
import pl.cyganki.utils.security.dto.User;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private RestTemplate restTemplate;
    private AuthService authService;

    @Autowired
    public AuthController(RestTemplate restTemplate, AuthService authService) {
        this.restTemplate = restTemplate;
        this.authService = authService;
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
        return authService.getLoggedInUser(userMap);
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

    @PostMapping("/logout")
    @ApiOperation("Endpoint for CHECKING if user can be logged out (it doesn't logout!). It allows API Gateway to clear user from cache on the gateway level. Returns ok if is user's token to be logged out")
    public ResponseEntity<String> logoutUser(@RequestHeader(value = GlobalValues.AUTH_TOKEN, required = false) String token) {
        boolean isToken = !StringUtils.isNullOrEmpty(token);

        if (isToken) {
            return ResponseEntity.ok("Can logout");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot logout");
        }
    }
}