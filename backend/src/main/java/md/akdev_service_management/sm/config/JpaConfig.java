package md.akdev_service_management.sm.config;

import md.akdev_service_management.sm.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {
    private final UserService userService;

    @Autowired
    public JpaConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SystemLoggedInUserAuditorAware auditorAware() {
        return new SystemLoggedInUserAuditorAware(userService);
    }
}
