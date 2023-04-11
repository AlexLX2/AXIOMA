package md.akdev_service_management.sm.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException{
    private String message = "no data found";
}
