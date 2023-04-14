package md.akdev_service_management.sm.dto.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import md.akdev_service_management.sm.dto.user.UserDTO;
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
public class TicketBodyDTO implements DTO {
    protected int id;
    protected int ticket;
    protected String body;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected LocalDateTime createdAt;
    protected List<TicketAttachmentLazyDTO> ticketAttachment;

    public TicketBodyDTO() {

    }

    @Override
    public ModelMapper updateModelMapper(ModelMapper mapper, MappingUtils mappingUtils){
        mapper.addMappings(ticketBodyMap(mappingUtils));
        return mapper;
    }

    PropertyMap<TicketBody, TicketBodyDTO> ticketBodyMap(MappingUtils mappingUtils) {
        return new PropertyMap<TicketBody, TicketBodyDTO>() {
            @Override
            protected void configure() {
                Converter<TicketBody, List<TicketAttachmentLazyDTO>> mapTicketBody = new AbstractConverter<TicketBody, List<TicketAttachmentLazyDTO>>() {
                    @Override
                    protected List<TicketAttachmentLazyDTO> convert(TicketBody ticketBody) {
                        return mappingUtils.mapList(ticketBody.getTicketAttachment(), TicketAttachmentLazyDTO.class);
                    }
                };

                using(mapTicketBody).map(source, destination.getTicketAttachment());


            }
        };
    }

}
