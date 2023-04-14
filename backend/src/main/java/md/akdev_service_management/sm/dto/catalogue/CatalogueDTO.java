package md.akdev_service_management.sm.dto.catalogue;

import lombok.Data;
import md.akdev_service_management.sm.models.catalogue.Catalogue;
import md.akdev_service_management.sm.utils.DTO;

@Data
public class CatalogueDTO implements DTO {
    private int id;
    private String name;
    private boolean valid;


    public CatalogueDTO(Catalogue catalogue){
        this.id = catalogue.getId();
        this.name = catalogue.getName();
        this.valid = catalogue.isValid();

    }


}
