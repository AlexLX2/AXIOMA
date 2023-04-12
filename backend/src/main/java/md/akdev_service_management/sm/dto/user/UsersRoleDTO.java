package md.akdev_service_management.sm.dto.user;

import lombok.Data;
import md.akdev_service_management.sm.utils.DTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UsersRoleDTO implements DTO {
    private int id;
    @NotEmpty(message = "Role name must be not empty")
    @Size(min = 2, max = 20, message = "Role name length must be between 2 and 20 charters")
    @Valid
    private String role;
    private List<UserDTO> users;
}
