package md.akdev_service_management.sm.utils;

import md.akdev_service_management.sm.controllers.ConfigController;
import md.akdev_service_management.sm.dto.ticket.TicketDTO;
import md.akdev_service_management.sm.services.mail.MailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import java.util.List;


@Component
public class MailReceiver {

    private final MailService mailService;
    private final ConfigController configController;

    private Boolean enableAutoFetch;

    public MailReceiver(MailService mailService, ConfigController configController) {
        this.mailService = mailService;
        this.configController = configController;
    }

    @Scheduled(fixedRate = 300000)
    public void executeTask() {
        this.enableAutoFetch = Boolean.valueOf(this.configController.getConfigByName("auto_fetch_email").getValue());

        if (this.enableAutoFetch) {
            List<Message> messageList = mailService.readEmails();
            for (Message message : messageList) {
                TicketDTO ticketDTO = convertMessageToTicketDTO(message);
            }
        }
    }

    private TicketDTO convertMessageToTicketDTO(Message message) {
        return null;
    }
}
