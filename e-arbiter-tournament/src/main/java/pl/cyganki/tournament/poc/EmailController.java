package pl.cyganki.tournament.poc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cyganki.tournament.service.MailService;
import pl.cyganki.utils.security.dto.User;

@Profile("dev")
@RestController
@RequestMapping("/poc/email")
@Slf4j
public class EmailController {

    private MailService emailService;

    @Autowired
    public EmailController(MailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/finished/{id}")
    public ResponseEntity<String> finishedTournament(@PathVariable("id") String tournamentId) {
        try {
            emailService.sendFinishedTournamentEmail(tournamentId);
            return ResponseEntity.ok("Email about finished tournament has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send finished tournament email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send finished tournament email: {}" + ex.getMessage());
        }
    }

    @GetMapping("/extend/{id}")
    public ResponseEntity<String> extendTournament(@PathVariable("id") String tournamentId) {
        try {
            emailService.sendExtendTournamentDeadlineEmail(tournamentId);
            return ResponseEntity.ok("Email about extend tournament has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send extend tournament email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send extend tournament email: {}" + ex.getMessage());
        }
    }

    @GetMapping("/removed/{id}/{user-id}")
    public ResponseEntity<String> removedUser(@PathVariable("id") String tournamentId, @PathVariable("user-id") long userId) {
        try {
            emailService.sendRemovedUserEmail(tournamentId, userId);
            return ResponseEntity.ok("Email about removing user has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send removing user email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send finished tournament email: {}" + ex.getMessage());
        }
    }

    @GetMapping("/broadcast")
    public ResponseEntity<String> adminBroadcast(User user) {
        try {
            emailService.sendAdminBroadcastEmail(user, "test tournamentinio", "Wiadomość testowa do wszystkich uczestników.");
            return ResponseEntity.ok("Admin broadcast email has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send admin email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send admin boardcast email: " + ex.getMessage());
        }
    }
}

