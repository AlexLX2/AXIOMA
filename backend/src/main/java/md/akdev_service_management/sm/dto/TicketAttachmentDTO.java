package md.akdev_service_management.sm.dto;

import lombok.Data;

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
