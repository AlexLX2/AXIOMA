package md.akdev_service_management.sm.dto.ticket;

import lombok.Data;
import md.akdev_service_management.sm.models.ticket.TicketBody;
import md.akdev_service_management.sm.utils.DTO;
import md.akdev_service_management.sm.utils.MappingUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class TicketBodyLazyDTO implements DTO{
    protected int id;
    protected String from;
    protected String to;
    protected String subject;
    protected String body;
    protected LocalDateTime createdAt;
    protected String createdBy;
    protected String changedBy;
    protected List<TicketAttachmentLazyDTO> ticketAttachment;


}
