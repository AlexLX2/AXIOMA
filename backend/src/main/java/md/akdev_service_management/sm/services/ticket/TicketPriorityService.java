package md.akdev_service_management.sm.services.ticket;

import md.akdev_service_management.sm.models.ticket.TicketPriority;
import md.akdev_service_management.sm.repositories.ticket.TicketPriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketPriorityService {
    private final TicketPriorityRepository ticketPriorityRepository;

    @Autowired
    public TicketPriorityService(TicketPriorityRepository ticketPriorityRepository) {
        this.ticketPriorityRepository = ticketPriorityRepository;
    }

    public  List<TicketPriority> findAll(){
       return ticketPriorityRepository.findAll();
    }

    public Optional<TicketPriority> findById(int id){
        return ticketPriorityRepository.findById(id);
    }
    @Transactional
    public void newTicketPriority(TicketPriority ticketPriority){
        ticketPriorityRepository.save(ticketPriority);
    }

    @Transactional
    public void update(TicketPriority ticketPriority){
        ticketPriorityRepository.findById(ticketPriority.getId()).ifPresent(
                ticketPriorityToUpdate ->{
                    ticketPriorityToUpdate.setName(ticketPriority.getName());
                    ticketPriorityToUpdate.setValid(ticketPriority.isValid());

                    ticketPriorityRepository.save(ticketPriorityToUpdate);
                }
        );
    }
}
