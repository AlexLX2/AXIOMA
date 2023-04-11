package md.akdev_service_management.sm.dto.ticket;

import lombok.Data;
import md.akdev_service_management.sm.dto.user.UserDTO;

@Data
public class TicketAttachmentDTO extends TicketAttachmentLazyDTO {
    private byte[] fileContent;
    private String fileType;
    private UserDTO createdBy;
    private UserDTO changedBy;

    public TicketAttachmentDTO(String fileName, byte[] fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }
    public TicketAttachmentDTO(){

    }
}
