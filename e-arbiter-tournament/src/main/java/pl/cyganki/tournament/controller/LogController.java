package pl.cyganki.tournament.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class LogController {
    @RequestMapping("/logs")
    String index(){
        log.info("INFO test message from tournament module");
        log.warn("WARN test message from tournament module");
        log.error("ERROR test message from tournament module");
        return "index";
    }
}