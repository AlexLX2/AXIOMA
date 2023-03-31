package md.akdev_service_management.sm.services;

import md.akdev_service_management.sm.models.TicketCategory;
import md.akdev_service_management.sm.repositories.TicketCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketCategoryService {
    private final TicketCategoryRepository ticketCategoryRepository;

    @Autowired
    public TicketCategoryService(TicketCategoryRepository ticketCategoryRepository) {
        this.ticketCategoryRepository = ticketCategoryRepository;
    }

    public List<TicketCategory> findAll(){
      return  ticketCategoryRepository.findAll();
    }

    public Optional<TicketCategory> findById(int id){
     return    this.ticketCategoryRepository.findById(id);
    }

    @Transactional
    public void newTicketCategory(TicketCategory ticketCategory){
        this.ticketCategoryRepository.save(ticketCategory);
    }

    @Transactional
    public void updateTicketCategory(TicketCategory ticketCategory){
        ticketCategoryRepository.findById(ticketCategory.getId()).ifPresent(
                ticketCategoryToUpdate ->{
                    ticketCategoryToUpdate.setName(ticketCategory.getName());
                    ticketCategoryToUpdate.setValid(ticketCategory.isValid());

                    this.ticketCategoryRepository.save(ticketCategoryToUpdate);
                }
        );
    }

}
