package pl.cyganki.tournament.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.tournament.service.EmailService;
import pl.cyganki.utils.security.dto.User;

@Profile("dev")
@RestController
@RequestMapping("/poc/email")
@Slf4j
public class EmailController {

    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/finishedTournament")
    public ResponseEntity<String> finishedTournament() {
        try {
            emailService.sendFinishedTournamentEmail("earbiterinfo@gmail.com", "test tournamentinio");
            return ResponseEntity.ok("Email about finished tournament has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send finished tournament email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send finished tournament email: {}" + ex.getMessage());
        }
    }

    @GetMapping("/extendTournament")
    public ResponseEntity<String> extendTournament() {
        try {
            emailService.sendExtendTournamentEmail("earbiterinfo@gmail.com", "test tournamentinio", "2017-09-31 12:00");
            return ResponseEntity.ok("Email about extend tournament has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send extend tournament email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send extend tournament email: {}" + ex.getMessage());
        }
    }

    @GetMapping("/adminBroadcast")
    public ResponseEntity<String> adminBroadcast(User user) {
        try {
            emailService.sendAdminBroadcastEmail("earbiterinfo@gmail.com", user, "test tournamentinio", "Wiadomość testowa do wszystkich uczestników.");
            return ResponseEntity.ok("Admin broadcast email has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send admin email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send admin boardcast email: " + ex.getMessage());
        }
    }
}

