package md.akdev_service_management.sm.dto.user;

import lombok.Data;
import md.akdev_service_management.sm.models.ticket.AdvanceInfo;
import md.akdev_service_management.sm.models.ticket.BasicInfo;
import md.akdev_service_management.sm.utils.DTO;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
public class UserCreateDTO  implements DTO {
    @NotEmpty(message = "The login must be not empty", groups = BasicInfo.class)
    @Size(min = 2, max = 20, message = "login length must be between 2 and 20 char")
    @Valid
    private String login;
    @NotEmpty(message = "The password is required", groups = BasicInfo.class)
    @Size(min = 8, max = 30, message = "password length must be between 2 and 20 char", groups = AdvanceInfo.class)
    private String password;
    @NotEmpty(message = "The first name is required.", groups = AdvanceInfo.class)
    @Size(min = 2, max = 40, message = "password length must be between 2 and 20 char", groups = AdvanceInfo.class)
    private String firstName;
    @NotEmpty(message = "The last name is required.", groups = AdvanceInfo.class)
    @Size(min = 2, max = 40, message = "password length must be between 2 and 20 char", groups = AdvanceInfo.class)
    private String lastName;
    @NotEmpty(message = "The email address is required.", groups = AdvanceInfo.class)
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE }, groups = AdvanceInfo.class)
    @Valid
    private String email;
}
