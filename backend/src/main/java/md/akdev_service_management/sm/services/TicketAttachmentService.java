package md.akdev_service_management.sm.services;

import md.akdev_service_management.sm.dto.TicketAttachmentDTO;
import md.akdev_service_management.sm.models.TicketAttachment;
import md.akdev_service_management.sm.models.TicketBody;
import md.akdev_service_management.sm.models.User;
import md.akdev_service_management.sm.repositories.TicketAttachmentRepository;
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

    public void storeFile(MultipartFile[] files, TicketBody ticketBody, User user) {

        Arrays.asList(files).stream().forEach(file -> {
            byte[] bytes = new byte[0];
            String fileName;
            try {
                bytes = file.getBytes();
                fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

                TicketAttachmentDTO ticketAttachmentDTO = new TicketAttachmentDTO(fileName, bytes);

                TicketAttachment ticketAttachment = convertToTicketAttachment(ticketAttachmentDTO);
                ticketAttachment.setTicketBody(ticketBody);
                ticketAttachment.setFileType(file.getContentType());
                ticketAttachment.setChangedBy(user);
                ticketAttachment.setCreatedBy(user);
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
