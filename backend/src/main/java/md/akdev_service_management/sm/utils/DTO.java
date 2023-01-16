package md.akdev_service_management.sm.utils;

import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public interface DTO {
    default ModelMapper updateModelMapper(ModelMapper mapper, MappingUtils utils){
        return mapper;
    }

    default String dateFormat(LocalDateTime dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        return simpleDateFormat.format(dateTime);
    }
}
