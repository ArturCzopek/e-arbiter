package pl.cyganki.tournament.poc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.tournament.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/finishedTournament")
    public String finishedTournament() {
        try {
            emailService.sendFinishedTournamentEmail("k2nder@gmail.com", "test userinio", "test tournamentinio");
            return "Email about finished tournament has been sent!";
        }catch(Exception ex) {
            return "Error while trying to send finished tournament email: " + ex;
        }
    }

    @RequestMapping("/extendTournament")
    public String extendTournament() {
        try {
            emailService.sendExtendTournamentEmail("k2nder@gmail.com", "test userinio", "test tournamentinio", "2017-09-31 12:00");
            return "Email about extend tournament has been sent!";
        }catch(Exception ex) {
            return "Error while trying to send extend tournament email: " + ex;
        }
    }

    @RequestMapping("/adminBroadcast")
    public String adminBroadcast() {
        try {
            emailService.sendAdminBroadcastEmail("k2nder@gmail.com", "test userinio", "test tournamentinio", "Wiadomość testowa do wszystkich uczestników.");
            return "Admin broadcast email has been sent!";
        }catch(Exception ex) {
            return "Error while trying to send admin broadcast email: " + ex;
        }
    }
}

