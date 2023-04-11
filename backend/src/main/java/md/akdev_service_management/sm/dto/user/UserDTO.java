package md.akdev_service_management.sm.dto.user;

import lombok.Data;
import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.utils.DTO;
import md.akdev_service_management.sm.utils.MappingUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;


@Data
public class UserDTO implements DTO {
    private int id;
    private String login;
    private String firstName;
    private String lastName;
    private boolean isValid;
    private String email;
    private List<RolesDTO> roles;


    @Override
    public ModelMapper updateModelMapper(ModelMapper mapper, MappingUtils mappingUtils){
        mapper.addMappings(userRoleMap(mappingUtils));
        return mapper;
    }

    PropertyMap<User, UserDTO> userRoleMap(MappingUtils mappingUtils) {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                Converter<User, List<RolesDTO>> mapUser = new AbstractConverter<>() {
                    @Override
                    protected List<RolesDTO> convert(User user) {
                        return mappingUtils.mapList(user.getRoles(), RolesDTO.class);
                    }
                };

                using(mapUser).map(source, destination.getRoles());

            }
        };
    }

    public UserDTO(){

    }

}
