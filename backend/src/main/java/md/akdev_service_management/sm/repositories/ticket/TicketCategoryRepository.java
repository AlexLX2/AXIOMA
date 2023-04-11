package md.akdev_service_management.sm.repositories.ticket;

import md.akdev_service_management.sm.models.ticket.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "category", path = "category")
public interface TicketCategoryRepository extends JpaRepository<TicketCategory,Integer> {
}
