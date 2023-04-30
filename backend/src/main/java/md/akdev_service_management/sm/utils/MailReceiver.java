package md.akdev_service_management.sm.utils;

import md.akdev_service_management.sm.controllers.ConfigController;
import md.akdev_service_management.sm.controllers.TicketController;
import md.akdev_service_management.sm.dto.catalogue.CategoryDTO;
import md.akdev_service_management.sm.dto.catalogue.PriorityDTO;
import md.akdev_service_management.sm.dto.catalogue.StatusDTO;
import md.akdev_service_management.sm.dto.ticket.TicketBodyDTO;
import md.akdev_service_management.sm.dto.ticket.TicketDTO;
import md.akdev_service_management.sm.models.acl.AclObjectIdentity;
import md.akdev_service_management.sm.services.mail.MailService;
import md.akdev_service_management.sm.services.ticket.TicketCategoryService;
import md.akdev_service_management.sm.services.ticket.TicketPriorityService;
import md.akdev_service_management.sm.services.ticket.TicketStatusService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Component
public class MailReceiver {

    private final MailService mailService;
    private final ConfigController configController;

    private final TicketStatusService statusService;
    private final TicketPriorityService priorityService;
    private final TicketCategoryService categoryService;

    private final MappingUtils mappingUtils;

    private final TicketController ticketController;



    public MailReceiver(MailService mailService, ConfigController configController, TicketStatusService statusService, TicketPriorityService priorityService, TicketCategoryService categoryService, MappingUtils mappingUtils, TicketController ticketController) {
        this.mailService = mailService;
        this.configController = configController;
        this.statusService = statusService;
        this.priorityService = priorityService;
        this.categoryService = categoryService;
        this.mappingUtils = mappingUtils;
        this.ticketController = ticketController;
    }

    @Scheduled(fixedRate = 300000)
    public void executeTask() {
        boolean enableAutoFetch = Boolean.parseBoolean(this.configController.getConfigByName("auto_fetch_email").getValue());

        if (enableAutoFetch) {
            List<Message> messageList = mailService.readEmails();
            for (Message message : messageList) {
                try {
                    TicketDTO ticketDTO = convertMessageToTicketDTO(message);
                    ticketController.createTicket(ticketDTO);

                } catch (MessagingException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private TicketDTO convertMessageToTicketDTO(Message message) throws MessagingException, IOException {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTitle(message.getSubject());
        ticketDTO.setAuthor(Arrays.stream(message.getFrom()).findFirst().toString());

        ticketDTO.setStatus(mappingUtils.map(
                statusService.findById(
                        Integer.parseInt(
                                configController.getConfigByName("default_status")
                                        .getValue())).get(),StatusDTO.class));
        ticketDTO.setPriority(mappingUtils.map(
                priorityService.findById(
                        Integer.parseInt(
                                configController.getConfigByName("default_priority").getValue()
                        )).get(), PriorityDTO.class
                ));
        ticketDTO.setCategory(mappingUtils.map(
                categoryService.findById(
                        Integer.parseInt(
                                configController.getConfigByName("default_cat").getValue()
                        )).get(), CategoryDTO.class
                ));

        ticketDTO.setAcl(new AclObjectIdentity());

        TicketBodyDTO ticketBodyDTO = new TicketBodyDTO();
        ticketBodyDTO.setBody(message.getContent().toString());

        ticketDTO.setTicketBody(Collections.singletonList(ticketBodyDTO));

        System.out.println("Ticket DTO: " + ticketDTO);
        return ticketDTO;
    }
}
