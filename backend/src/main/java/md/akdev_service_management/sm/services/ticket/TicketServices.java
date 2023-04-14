package md.akdev_service_management.sm.services.ticket;

import md.akdev_service_management.sm.exceptions.NotFoundException;
import md.akdev_service_management.sm.models.ticket.Ticket;
import md.akdev_service_management.sm.repositories.ticket.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional(readOnly = false)
public class TicketServices {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServices(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
//    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public Optional<Ticket> findById(int id) {
        return Optional.ofNullable(ticketRepository.findByTicketId(id).orElseThrow(NotFoundException::new));
    }

    public Page<Ticket> findAll(Pageable pageable){
        return ticketRepository.findAll(pageable);
    }

    public long findCount(){
        return ticketRepository.count();
    }

    @Transactional
    public void newTicket(Ticket ticket){
        ticketRepository.save(ticket);

    }
}
