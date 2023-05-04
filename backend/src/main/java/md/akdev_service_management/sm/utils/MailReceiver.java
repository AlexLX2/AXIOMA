package md.akdev_service_management.sm.utils;

import md.akdev_service_management.sm.controllers.ConfigController;
import md.akdev_service_management.sm.controllers.TicketController;
import md.akdev_service_management.sm.dto.catalogue.CategoryDTO;
import md.akdev_service_management.sm.dto.catalogue.PriorityDTO;
import md.akdev_service_management.sm.dto.catalogue.StatusDTO;
import md.akdev_service_management.sm.dto.ticket.TicketBodyDTO;
import md.akdev_service_management.sm.dto.ticket.TicketDTO;
import md.akdev_service_management.sm.models.acl.AclObjectIdentity;
import md.akdev_service_management.sm.services.config.ConfigService;
import md.akdev_service_management.sm.services.mail.MailService;
import md.akdev_service_management.sm.services.ticket.TicketCategoryService;
import md.akdev_service_management.sm.services.ticket.TicketPriorityService;
import md.akdev_service_management.sm.services.ticket.TicketStatusService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final TicketStatusService statusService;
    private final TicketPriorityService priorityService;
    private final TicketCategoryService categoryService;

    private final MappingUtils mappingUtils;

    private final TicketController ticketController;


    private final ConfigService configService;

    private final UserDetailsService userDetailsService;


    public MailReceiver(MailService mailService, TicketStatusService statusService, TicketPriorityService priorityService, TicketCategoryService categoryService, MappingUtils mappingUtils, TicketController ticketController, ConfigService configService, UserDetailsService userDetailsService) {
        this.mailService = mailService;
        this.statusService = statusService;
        this.priorityService = priorityService;
        this.categoryService = categoryService;
        this.mappingUtils = mappingUtils;
        this.ticketController = ticketController;
        this.configService = configService;
        this.userDetailsService = userDetailsService;
    }

    @Scheduled(fixedRate = 300000)
    public void executeTask() {


        String login = configService.getValue("default_mail_user");

        UserDetails userDetails = userDetailsService.loadUserByUsername(login);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());

        try {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {
            e.getCause();
        }

        boolean enableAutoFetch = Boolean.parseBoolean(configService.getValue("auto_fetch_email"));

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
        ticketDTO.setAuthor(String.valueOf(Arrays.stream(message.getFrom()).findFirst().get()));

        ticketDTO.setStatus(mappingUtils.map(
                statusService.findById(
                        Integer.parseInt(
                                configService.getValue("default_status"))).get(), StatusDTO.class));
        ticketDTO.setPriority(mappingUtils.map(
                priorityService.findById(
                        Integer.parseInt(
                                configService.getValue("default_priority"))).get(), PriorityDTO.class
        ));
        ticketDTO.setCategory(mappingUtils.map(
                categoryService.findById(
                        Integer.parseInt(
                                configService.getValue("default_cat"))).get(), CategoryDTO.class
        ));

        AclObjectIdentity aclObjectIdentity = new AclObjectIdentity();
        aclObjectIdentity.setId(Long.valueOf(configService.getValue("default_acl")));
        ticketDTO.setAcl(aclObjectIdentity);

        TicketBodyDTO ticketBodyDTO = new TicketBodyDTO();


        ticketBodyDTO.setBody(message.getContent().toString());

        ticketDTO.setTicketBody(Collections.singletonList(ticketBodyDTO));

        System.out.println("Ticket DTO: " + ticketDTO);
        return ticketDTO;
    }
}
