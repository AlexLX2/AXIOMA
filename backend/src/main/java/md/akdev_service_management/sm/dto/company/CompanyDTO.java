package md.akdev_service_management.sm.dto.company;

import lombok.Data;
import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.utils.DTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CompanyDTO implements DTO {

    private Integer id;

    @NotEmpty(message = "Company name must be not empty")
    @Size(min = 2, max = 20, message = "Company name length must be between 2 and 20 char")
    @Valid
    private String name;

    private String address;

    private String url;

    private String comments;

}
