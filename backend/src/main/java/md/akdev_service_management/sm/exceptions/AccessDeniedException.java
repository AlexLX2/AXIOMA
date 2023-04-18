package md.akdev_service_management.sm.exceptions;

public class AccessDeniedException extends org.springframework.security.access.AccessDeniedException {
    public AccessDeniedException(String msg) {
        super(msg);
    }

}
