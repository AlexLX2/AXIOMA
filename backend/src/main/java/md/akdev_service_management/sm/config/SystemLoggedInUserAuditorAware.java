package md.akdev_service_management.sm.config;

import md.akdev_service_management.sm.models.User;
import md.akdev_service_management.sm.services.UserService;
import md.akdev_service_management.sm.utils.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
public class SystemLoggedInUserAuditorAware implements AuditorAware<User> {
    private final UserService userService;
    @Autowired
    public SystemLoggedInUserAuditorAware(UserService userService) {
        this.userService = userService;
    }

    @Override
    public @NotNull Optional<User> getCurrentAuditor() {

        return Optional.ofNullable(userService.finByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(NotFoundException::new));
    }
}
