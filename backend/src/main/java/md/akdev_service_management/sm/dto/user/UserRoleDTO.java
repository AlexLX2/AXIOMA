package md.akdev_service_management.sm.dto.user;

import lombok.Data;
import md.akdev_service_management.sm.utils.DTO;

@Data
public class UserRoleDTO implements DTO {
    private int id;
    private String user;
    private String role;

}
