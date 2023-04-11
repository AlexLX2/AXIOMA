package md.akdev_service_management.sm.dto.user;

import lombok.Data;
import md.akdev_service_management.sm.utils.DTO;

import java.util.List;

@Data
public class UserRolesDTO implements DTO {
    private String user;
    private List<RolesDTO> role;
}
