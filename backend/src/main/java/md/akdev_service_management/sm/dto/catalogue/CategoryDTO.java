package md.akdev_service_management.sm.dto.catalogue;

import lombok.Data;
import md.akdev_service_management.sm.utils.DTO;

@Data
public class CategoryDTO implements DTO {
    Integer id;
    String name;
}
