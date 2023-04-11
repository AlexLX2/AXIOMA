package md.akdev_service_management.sm.services.ticket;

import md.akdev_service_management.sm.models.ticket.TicketStatus;
import md.akdev_service_management.sm.repositories.ticket.TicketStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketStatusService {
    private final TicketStatusRepository ticketStatusRepository;

    @Autowired
    public TicketStatusService(TicketStatusRepository ticketStatusRepository) {
        this.ticketStatusRepository = ticketStatusRepository;
    }

    public List<TicketStatus> findAll(){
        return ticketStatusRepository.findAll();
    }

    public Optional<TicketStatus> findById(int id){
        return ticketStatusRepository.findById(id);
    }

    @Transactional
    public void newTicketStatus(TicketStatus ticketStatus){
        ticketStatusRepository.save(ticketStatus);
    }

    @Transactional
    public void updateTicketStatus(TicketStatus ticketStatus){
        ticketStatusRepository.findById(ticketStatus.getId()).ifPresent(
                ticketStatusToUpdate ->{
                    ticketStatusToUpdate.setName(ticketStatus.getName());
                    ticketStatusToUpdate.setValid(ticketStatus.isValid());

                    ticketStatusRepository.save(ticketStatusToUpdate);
                }
        );
    }


}
