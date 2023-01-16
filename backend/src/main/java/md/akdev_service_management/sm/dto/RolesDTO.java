package md.akdev_service_management.sm.dto;

import lombok.Data;
import md.akdev_service_management.sm.utils.DTO;

@Data
public class RolesDTO implements DTO {
    private int id;
    private String name;
}
