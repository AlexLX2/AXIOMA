package md.akdev_service_management.sm.services.ticket;

import md.akdev_service_management.sm.controllers.MailController;
import md.akdev_service_management.sm.models.ticket.Ticket;
import md.akdev_service_management.sm.repositories.ticket.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = false)
public class TicketServices {
    private final TicketRepository ticketRepository;
    private final MailController mailController;

    @Autowired
    public TicketServices(TicketRepository ticketRepository, MailController mailController) {
        this.ticketRepository = ticketRepository;
        this.mailController = mailController;
    }

   @PostAuthorize("hasPermission(returnObject, 'READ')")
    public Ticket findById(Integer id) {
        return ticketRepository.findByTicketId(id);
    }

    public Page<Ticket> findAll(Pageable pageable){
        return ticketRepository.findAll(pageable);
    }

    public long findCount(){
        return ticketRepository.count();
    }

    @Transactional
    @PreAuthorize("hasPermission(#ticket, 'WRITE')")
    public void newTicket(Ticket ticket){
        ticketRepository.save(ticket);
        mailController.sendSimpleMessage(ticket.getCreatedBy().getEmail(),ticket.getTitle(),ticket.getTicketBody().get(0).getBody());

    }
}
