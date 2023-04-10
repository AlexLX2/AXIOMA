package md.akdev_service_management.sm.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
@Getter
@Setter
public class DuplicateException extends DataIntegrityViolationException {
    public DuplicateException(String msg) {
        super(msg);
    }

}
