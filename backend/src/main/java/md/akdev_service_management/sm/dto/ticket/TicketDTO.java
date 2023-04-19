package md.akdev_service_management.sm.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import md.akdev_service_management.sm.dto.catalogue.CategoryDTO;
import md.akdev_service_management.sm.dto.catalogue.PriorityDTO;
import md.akdev_service_management.sm.dto.catalogue.StatusDTO;
import md.akdev_service_management.sm.dto.user.RolesDTO;
import md.akdev_service_management.sm.models.acl.AclObjectIdentity;
import md.akdev_service_management.sm.models.ticket.Ticket;
import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.utils.DTO;
import md.akdev_service_management.sm.utils.MappingUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonFilter("ticketDTOFilter")
public class TicketDTO implements DTO {

    private int ticketId;
    @NotNull(message =" Ticket title must be not empty")
    @Size(min = 2, max = 200, message = "Ticket title length must be between 2 and 20 charters")
    private String title;
    @NotNull(message = "Ticket author must not be empty (maybe customer user didn't fully setup)")
    @Size(min= 4, max=200, message = "Ticket title length must be between 4 and 200 characters")
    private String author;
    @NotNull(message = "Ticket status must be not empty")
    private StatusDTO status;
    @NotNull(message = "Ticket priority must be not empty")
    private PriorityDTO priority;
    @NotNull(message = "Ticket category must be not empty")
    private CategoryDTO category;
    @NotNull(message = "You probably or your role don't have rights to create ticket")
    private AclObjectIdentity acl;
    private List<TicketBodyDTO> ticketBody;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime changedAt;

    @Override
    public ModelMapper updateModelMapper(ModelMapper mapper, MappingUtils mappingUtils){
        mapper.addMappings(ticketMap(mappingUtils));
        return mapper;
    }
    PropertyMap<Ticket, TicketDTO> ticketMap(MappingUtils mappingUtils) {
        return new PropertyMap<Ticket, TicketDTO>() {
            @Override
            protected void configure() {
                Converter<Ticket, List<TicketBodyDTO>> mapTicket = new AbstractConverter<Ticket,  List<TicketBodyDTO>>() {
                    @Override
                    protected  List<TicketBodyDTO> convert(Ticket ticket) {
                        return mappingUtils.mapList(ticket.getTicketBody(), TicketBodyDTO.class);
                    }
                };

                using(mapTicket).map(source, destination.getTicketBody());

            }
        };
    }

}
