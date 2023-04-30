package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.services.mail.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import java.util.List;
import java.util.Map;

@RestController
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }


    @GetMapping("/get-mail")
    public ResponseEntity<?> getMail(){
        List<Message> messageList = mailService.readEmails();
        return ResponseEntity.ok(Map.of("new messages", messageList.size()));
    }
}
