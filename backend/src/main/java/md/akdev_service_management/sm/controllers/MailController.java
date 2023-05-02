package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.services.mail.MailService;
import org.springframework.stereotype.Controller;

import javax.mail.Message;
import java.util.List;

@Controller
public class MailController {

    private final MailService mailService;


    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    public List<Message> getMail(){
        return mailService.readEmails();
    }

    public void sendSimpleMessage(String to, String subject, String text) {
       mailService.sendSimpleMessage(to,subject,text);
    }
}
