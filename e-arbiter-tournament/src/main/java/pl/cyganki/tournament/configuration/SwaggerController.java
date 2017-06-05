package pl.cyganki.tournament.configuration;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Profile({"dev", "test"})
public class SwaggerController {

    private static final String SWAGGER_URL = "redirect:/swagger-ui.html";

    @RequestMapping(value = "${e-arbiter.swagger.path}", method = RequestMethod.GET)
    public String swaggerMapping() {
        return SWAGGER_URL;
    }
}