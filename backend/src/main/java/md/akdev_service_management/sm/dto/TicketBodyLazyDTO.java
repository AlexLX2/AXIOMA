package md.akdev_service_management.sm.dto;

import lombok.Data;
import md.akdev_service_management.sm.models.TicketBody;
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

    @Override
    public ModelMapper updateModelMapper(ModelMapper mapper, MappingUtils mappingUtils){
        mapper.addMappings(ticketBodyMap(mappingUtils));
        return mapper;
    }

    PropertyMap<TicketBody, TicketBodyLazyDTO> ticketBodyMap(MappingUtils mappingUtils) {
        return new PropertyMap<TicketBody, TicketBodyLazyDTO>() {
            @Override
            protected void configure() {
                Converter<TicketBody, List<TicketAttachmentLazyDTO>> mapTicketBody = new AbstractConverter<TicketBody, List<TicketAttachmentLazyDTO>>() {
                    @Override
                    protected List<TicketAttachmentLazyDTO> convert(TicketBody ticketBody) {
                        return mappingUtils.mapList(ticketBody.getTicketAttachment(), TicketAttachmentLazyDTO.class);
                    }
                };

                using(mapTicketBody).map(source, destination.getTicketAttachment());

                map().setChangedBy(source.getChangedBy().getFullName());
                map().setCreatedBy(source.getCreatedBy().getFullName());

            }
        };
    }

}
