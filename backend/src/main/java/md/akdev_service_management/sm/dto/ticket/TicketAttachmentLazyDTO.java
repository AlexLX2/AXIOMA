package md.akdev_service_management.sm.dto.ticket;

import lombok.Data;
import md.akdev_service_management.sm.utils.DTO;

@Data
public class TicketAttachmentLazyDTO implements DTO {
    protected int id;
    protected String fileName;

}
