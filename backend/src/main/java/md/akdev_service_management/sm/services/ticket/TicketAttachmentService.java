package md.akdev_service_management.sm.services.ticket;

import md.akdev_service_management.sm.dto.ticket.TicketAttachmentDTO;
import md.akdev_service_management.sm.models.ticket.TicketAttachment;
import md.akdev_service_management.sm.models.ticket.TicketBody;
import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.repositories.ticket.TicketAttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = false)
public class TicketAttachmentService {

    private final TicketAttachmentRepository ticketAttachmentRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public TicketAttachmentService(TicketAttachmentRepository ticketAttachmentRepository, ModelMapper modelMapper) {
        this.ticketAttachmentRepository = ticketAttachmentRepository;
        this.modelMapper = modelMapper;
    }

    public void storeFile(MultipartFile[] files, TicketBody ticketBody) {

        Arrays.stream(files).forEach(file -> {
            byte[] bytes ;
            String fileName;
            try {
                bytes = file.getBytes();
                fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

                TicketAttachmentDTO ticketAttachmentDTO = new TicketAttachmentDTO(fileName, bytes);

                TicketAttachment ticketAttachment = convertToTicketAttachment(ticketAttachmentDTO);
                ticketAttachment.setTicketBody(ticketBody);
                ticketAttachment.setFileType(file.getContentType());
                ticketAttachmentRepository.save(ticketAttachment);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public List<TicketAttachment> getByTicketBodyId(int id){
        return ticketAttachmentRepository.findTicketAttachmentsByTicketBodyId(id);
    }

    public TicketAttachment getById(int id){
        return ticketAttachmentRepository.findById(id).orElse(new TicketAttachment());
    }

    private TicketAttachment convertToTicketAttachment(TicketAttachmentDTO ticketAttachmentDTO){
        return modelMapper.map(ticketAttachmentDTO, TicketAttachment.class);
    }

}
