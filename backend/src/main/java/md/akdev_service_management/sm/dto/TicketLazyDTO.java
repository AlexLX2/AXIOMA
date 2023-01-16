package md.akdev_service_management.sm.dto;

import lombok.Data;
import md.akdev_service_management.sm.models.Ticket;
import md.akdev_service_management.sm.utils.DTO;
import md.akdev_service_management.sm.utils.MappingUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketLazyDTO implements DTO {
    private int ticketId;
    private String title;
    private String author;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime changedAt;
    private String changedBy;
    private String status;
    private String priority;
    private String category;
    private List<TicketBodyLazyDTO> ticketBody;

    @Override
    public ModelMapper updateModelMapper(ModelMapper mapper, MappingUtils mappingUtils){
        mapper.addMappings(ticketMap(mappingUtils));
        return mapper;
    }

    PropertyMap<Ticket, TicketLazyDTO> ticketMap(MappingUtils mappingUtils) {
        return new PropertyMap<Ticket, TicketLazyDTO>() {
            @Override
            protected void configure() {
                Converter<Ticket, List<TicketBodyLazyDTO>> mapTicket = new AbstractConverter<Ticket, List<TicketBodyLazyDTO>>() {
                    @Override
                    protected List<TicketBodyLazyDTO> convert(Ticket ticket) {
                        return mappingUtils.mapList(ticket.getTicketBody(), TicketBodyLazyDTO.class);
                    }
                };

                using(mapTicket).map(source, destination.getTicketBody());

                map().setChangedBy(source.getChangedBy().getFullName());
                map().setCreatedBy(source.getCreatedBy().getFullName());
                map().setStatus(source.getStatus().getName());
                map().setPriority(source.getPriority().getName());
                map().setCategory(source.getCategory().getName());

            }
        };
    }
}
