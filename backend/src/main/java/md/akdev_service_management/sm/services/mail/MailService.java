package md.akdev_service_management.sm.services.mail;

import com.sun.mail.imap.protocol.FLAGS;
import md.akdev_service_management.sm.services.config.ConfigService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Service
public class MailService {

    private final ConfigService configService;
    private final JavaMailSender mailSender;

    public MailService(ConfigService configService, JavaMailSender mailSender) {
        this.configService = configService;
        this.mailSender = mailSender;
    }

    public List<Message> readEmails() {

        String email = configService.getValue("email");
        String password = configService.getValue("password");
        String mailServer = configService.getValue("mailServer");

        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.put("mail.imap.starttls.enable", "true");
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imap");
            store.connect(mailServer, email, password);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            Arrays.stream(messages).forEach( (message -> {
                try {
                    message.setFlag(Flags.Flag.SEEN, true);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }));
            inbox.close(true);
            store.close();
            return Arrays.asList(messages);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
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
