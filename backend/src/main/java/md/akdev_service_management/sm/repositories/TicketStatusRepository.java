package md.akdev_service_management.sm.repositories;

import md.akdev_service_management.sm.models.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(collectionResourceRel = "status", path = "status")
public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer> {

}
