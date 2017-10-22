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

/**
 * Simple poc controller for checking email templates. If possible, mail is sent automatically only to
 * user which triggered this poc request. Everything is "GET" because it's easy and simple to test.
 * In application, mail sending will be just added to proper functions, but endpoint for sending email will be available
 * only for admin broadcast
 */

@Profile("dev")
@RestController
@RequestMapping("/poc/email")
@Slf4j
public class EmailController {

    private MailService mailService;

    @Autowired
    public EmailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/finished/{id}")
    public ResponseEntity<String> finishedTournament(@PathVariable("id") String tournamentId) {
        try {
            mailService.sendFinishedTournamentEmail(tournamentId);
            return ResponseEntity.ok("Email about finished tournament has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send finished tournament email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send finished tournament email: {}" + ex.getMessage());
        }
    }

    @GetMapping("/extend/{id}")
    public ResponseEntity<String> extendTournament(@PathVariable("id") String tournamentId) {
        try {
            mailService.sendExtendTournamentDeadlineEmail(tournamentId);
            return ResponseEntity.ok("Email about extend tournament has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send extend tournament email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send extend tournament email: " + ex.getMessage());
        }
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<String> activateTournament(@PathVariable("id") String tournamentId) {
        try {
            mailService.sendActivateTournamentEmail(tournamentId);
            return ResponseEntity.ok("Email about activating tournament has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send extend tournament email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send extend tournament email: " + ex.getMessage());
        }
    }

    @GetMapping("/removed/{id}")
    public ResponseEntity<String> removedUser(@PathVariable("id") String tournamentId, User user) {
        try {
            mailService.sendRemovedUserEmail(tournamentId, user.getId());
            return ResponseEntity.ok("Email about removing user has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send removing user email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send finished tournament email: " + ex.getMessage());
        }
    }

    @GetMapping("/broadcast")
    public ResponseEntity<String> adminBroadcast(User user) {
        try {
            mailService.sendAdminBroadcastEmail(user, "test tournamentinio", "Wiadomość testowa do wszystkich uczestników.");
            return ResponseEntity.ok("Admin broadcast email has been sent!");
        } catch (Exception ex) {
            log.warn("Error while trying to send admin email: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while trying to send admin boardcast email: " + ex.getMessage());
        }
    }
}

