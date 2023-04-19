package md.akdev_service_management.sm.dto.ticket;

import lombok.Data;
import md.akdev_service_management.sm.dto.user.UserDTO;
import md.akdev_service_management.sm.utils.DTO;

@Data
public class TicketAttachmentDTO  implements DTO {
    private byte[] fileContent;
    private String fileType;
    private String fileName;

    public TicketAttachmentDTO(String fileName, byte[] fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }
    public TicketAttachmentDTO(){

    }
}
