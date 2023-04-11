package md.akdev_service_management.sm.dto.user;

import lombok.Data;
import md.akdev_service_management.sm.utils.DTO;

import java.util.List;

@Data
public class UsersRoleDTO implements DTO {
    private String role;
    private List<UserDTO> users;
}
