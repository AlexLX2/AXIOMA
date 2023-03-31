package md.akdev_service_management.sm.utils;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException{
    private String message = "no data found";
}
