package md.akdev_service_management.sm.repositories;

import md.akdev_service_management.sm.models.TicketAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketAttachmentRepository extends JpaRepository<TicketAttachment, Integer> {
    List<TicketAttachment> findTicketAttachmentsByTicketBodyId(int id);

}
