package md.akdev_service_management.sm.services;

import md.akdev_service_management.sm.models.Ticket;
import md.akdev_service_management.sm.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = false)
public class TicketServices {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServices(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public Ticket findById(int id) {
        return ticketRepository.findByTicketId(id).orElseThrow();
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
