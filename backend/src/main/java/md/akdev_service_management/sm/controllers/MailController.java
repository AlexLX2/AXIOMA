package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.services.mail.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

import javax.mail.Message;
import java.util.List;

@Controller
public class MailController {

    private final MailService mailService;
    private final JavaMailSender mailSender;

    public MailController(MailService mailService, JavaMailSender mailSender) {
        this.mailService = mailService;
        this.mailSender = mailSender;
    }

    public List<Message> getMail(){
        return mailService.readEmails();
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("aksema@akdev.md");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
