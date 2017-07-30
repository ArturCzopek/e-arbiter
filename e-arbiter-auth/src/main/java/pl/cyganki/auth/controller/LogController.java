package pl.cyganki.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class LogController {
    @RequestMapping("/logs")
    String index(){
        log.info("INFO test message from auth module");
        log.warn("WARN test message from auth module");
        log.error("ERROR test message from auth module");
        return "index";
    }
}