package md.akdev_service_management.sm;

import md.akdev_service_management.sm.utils.MappingUtils;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class ServicemanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicemanagementApplication.class, args);

		}
	@Bean
	ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	MappingUtils mappingUtils(){
		return new MappingUtils();
	}



}
