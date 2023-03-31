package md.akdev_service_management.sm.utils;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateException extends DataIntegrityViolationException {
    public DuplicateException(String msg) {
        super(msg);
    }

}
