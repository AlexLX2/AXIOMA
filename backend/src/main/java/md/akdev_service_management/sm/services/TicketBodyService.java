package md.akdev_service_management.sm.services;

import md.akdev_service_management.sm.models.TicketBody;
import md.akdev_service_management.sm.repositories.TicketBodyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
public class TicketBodyService {
    private final TicketBodyRepository ticketBodyRepository;

    @Autowired
    public TicketBodyService(TicketBodyRepository ticketBodyRepository) {
        this.ticketBodyRepository = ticketBodyRepository;
    }

    public List<TicketBody> getTicketBodiesByTicketId(int id){
        return ticketBodyRepository.findTicketBodiesByTicketTicketId(id);
    }
    @Transactional
    public void saveTicketBody(TicketBody ticketBody){
        ticketBodyRepository.save(ticketBody);
    }

    public Optional<TicketBody> getTicketBodyById(int id){
        return ticketBodyRepository.findById(id).stream().findAny();
    }


}
