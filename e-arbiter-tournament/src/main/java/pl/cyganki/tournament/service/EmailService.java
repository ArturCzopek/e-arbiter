package pl.cyganki.tournament.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailService {

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    private String buildFinishedTournamentEmail(String userName, String tournamentName) {
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("tournamentName", tournamentName);
        return templateEngine.process("FinishedTournamentEmailTemplate", context);
    }

    private String buildExtendTournamentEmail(String userName, String tournamentName, String newDeadline) {
        Context context = new Context();
        context.setVariable("userName", userName);
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

    public void sendFinishedTournamentEmail(String recipient, String userName, String tournamentName) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(recipient);
            messageHelper.setSubject("E-arbiter - wygaśnięcie turnieju " + tournamentName);
            String content = buildFinishedTournamentEmail(userName, tournamentName);
            messageHelper.setText(content, true);
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception
        }
    }

    public void sendExtendTournamentEmail(String recipient, String userName, String tournamentName, String newDeadline) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(recipient);
            messageHelper.setSubject("E-arbiter - przedłużenie terminu zakończenia turnieju " + tournamentName);
            String content = buildExtendTournamentEmail(userName, tournamentName, newDeadline);
            messageHelper.setText(content, true);
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception
        }
    }

    public void sendAdminBroadcastEmail(String recipient, String userName, String tournamentName, String message) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(recipient);
            messageHelper.setSubject("E-arbiter - wiadomość od admina turnieju " + tournamentName);
            String content = buildAdminBroadcastEmail(userName, tournamentName, message);
            messageHelper.setText(content, true);
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception
        }
    }

}
