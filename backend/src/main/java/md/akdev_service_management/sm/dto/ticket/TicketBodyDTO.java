package md.akdev_service_management.sm.dto.ticket;

import lombok.Data;
import md.akdev_service_management.sm.dto.user.UserDTO;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class TicketBodyDTO {
    protected int id;
    protected TicketDTO ticket;
    protected String from;
    protected String to;
    protected String subject;
    protected String body;
    protected LocalDateTime createdAt;
    protected UserDTO createdBy;
    protected UserDTO changedBy;
    protected List<TicketAttachmentLazyDTO> ticketAttachment;

    public TicketBodyDTO() {

    }

}
