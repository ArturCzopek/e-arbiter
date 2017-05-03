package pl.cyganki.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @Value("${e-arbiter.clientUrl}")
    private String clientUrl;

    @RequestMapping("/clientUrl")
    public String getClientUrl() {
        return clientUrl;
    }
}
