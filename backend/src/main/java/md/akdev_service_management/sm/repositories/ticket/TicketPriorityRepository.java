package md.akdev_service_management.sm.repositories.ticket;

import md.akdev_service_management.sm.models.ticket.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "priority", path = "priority")
public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Integer> {
}
