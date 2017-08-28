package pl.cyganki.tournament.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.cyganki.utils.security.dto.User;


@Service
@Slf4j
public class EmailService {

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendFinishedTournamentEmail(String recipient, String tournamentName) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(recipient);
            messageHelper.setSubject("e-Arbiter - wygaśnięcie turnieju " + tournamentName);
            String content = buildFinishedTournamentEmail(tournamentName);
            messageHelper.setText(content, true);
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            log.warn(e.getMessage());
        }
    }

    public void sendExtendTournamentEmail(String recipient, String tournamentName, String newDeadline) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(recipient);
            messageHelper.setSubject("e-Arbiter - przedłużenie terminu zakończenia turnieju " + tournamentName);
            String content = buildExtendTournamentEmail(tournamentName, newDeadline);
            messageHelper.setText(content, true);
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception
        }
    }

    public void sendAdminBroadcastEmail(String recipient, User user, String tournamentName, String message) {

        if (user.getRoles().stream().noneMatch(role -> "admin".equalsIgnoreCase(role.getName()))) {
            throw new SecurityException("User " + user.getName() + " cannot send an admin email!");
        }

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(recipient);
            messageHelper.setSubject("e-Arbiter - wiadomość od admina turnieju " + tournamentName);
            String content = buildAdminBroadcastEmail(user.getName(), tournamentName, message);
            messageHelper.setText(content, true);
        };

        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception
        }
    }

    private String buildFinishedTournamentEmail(String tournamentName) {
        Context context = new Context();
        context.setVariable("userName", "e-Arbiter");
        context.setVariable("tournamentName", tournamentName);
        return templateEngine.process("FinishedTournamentEmailTemplate", context);
    }

    private String buildExtendTournamentEmail(String tournamentName, String newDeadline) {
        Context context = new Context();
        context.setVariable("userName", "e-Arbiter");
        context.setVariable("tournamentName", tournamentName);
        context.setVariable("newDeadline", newDeadline);
        return templateEngine.process("ExtendTournamentEmailTemplate", context);
    }

    private String buildAdminBroadcastEmail(String userName, String tournamentName, String message) {
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("tournamentName", tournamentName);
        context.setVariable("message", message);
        return templateEngine.process("AdminBroadcastEmailTemplate", context);
    }
}
