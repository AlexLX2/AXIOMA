package md.akdev_service_management.sm.dto;

import lombok.Data;
import md.akdev_service_management.sm.utils.DTO;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
public class UserCreateDTO  implements DTO {
    @NotEmpty(message = "The login must be not empty")
    @Size(min = 2, max = 20, message = "login length must be between 2 and 20 char")
    @Valid
    private String login;
    @NotEmpty(message = "The password is required")
    @Size(min = 8, max = 30, message = "password length must be between 2 and 20 char")
    private String password;
    @NotEmpty(message = "The first name is required.")
    @Size(min = 2, max = 40, message = "password length must be between 2 and 20 char")
    private String firstName;
    @NotEmpty(message = "The last name is required.")
    @Size(min = 2, max = 40, message = "password length must be between 2 and 20 char")
    private String lastName;
    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    @Valid
    private String email;
}
