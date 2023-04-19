package md.akdev_service_management.sm.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;
import md.akdev_service_management.sm.models.ticket.TicketAttachment;
import md.akdev_service_management.sm.utils.DTO;
import md.akdev_service_management.sm.utils.MappingUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.Base64;

@Data
@JsonFilter("ticketDTOFilter")
public class TicketAttachmentLazyDTO implements DTO {
    protected int id;
    protected String fileName;
    protected String fileContent;
    protected String fileType;

}

