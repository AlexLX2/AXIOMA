package md.akdev_service_management.sm.dto.ticket;

import lombok.Data;
import md.akdev_service_management.sm.dto.catalogue.CatalogueDTO;
import md.akdev_service_management.sm.dto.user.UserDTO;
import md.akdev_service_management.sm.utils.DTO;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketDTO implements DTO {

    private int ticketId;
    private String title;
    private String author;
    private LocalDateTime createdAt;
    private UserDTO createdBy;
    private LocalDateTime changedAt;
    private UserDTO changedBy;
    private CatalogueDTO status;
    private CatalogueDTO priority;
    private CatalogueDTO category;
    private List<TicketBodyDTO> ticketBody;

}
