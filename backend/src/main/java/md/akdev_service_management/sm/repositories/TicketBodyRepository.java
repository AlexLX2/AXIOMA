package md.akdev_service_management.sm.repositories;

import md.akdev_service_management.sm.models.TicketBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketBodyRepository extends JpaRepository<TicketBody, Integer> {

    List<TicketBody> findTicketBodiesByTicketTicketId(int id);
}
