package md.akdev_service_management.sm.dto;

import lombok.Data;
import md.akdev_service_management.sm.models.Catalogue;
import md.akdev_service_management.sm.utils.DTO;

@Data
public class CatalogueDTO implements DTO {
    private int id;
    private String name;
    private boolean valid;
    private String createdBy;
    private String changedBy;

    public CatalogueDTO(Catalogue catalogue){
        this.id = catalogue.getId();
        this.name = catalogue.getName();
        this.valid = catalogue.isValid();
        this.changedBy = catalogue.getChangedBy().getFullName();
        this.createdBy = catalogue.getCreatedBy().getFullName();
    }


}
