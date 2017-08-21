package pl.cyganki.tournament.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.cyganki.tournament.service.EmailService;

@Controller
public class EmailController {

    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/simpleemail")
    @ResponseBody
    String home() {
        try {
            emailService.sendEmail("k2nder@gmail.com", "Testing email from e-arbiter bla bla bla");
            return "Email Sent!";
        }catch(Exception ex) {
            return "Error in sending email: " + ex;
        }
    }
}
