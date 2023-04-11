package md.akdev_service_management.sm.config;

import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.security.UsersDetails;
import md.akdev_service_management.sm.services.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
public class SystemLoggedInUserAuditorAware implements AuditorAware<User> {
    private final UserService userService;
    @Autowired
    public SystemLoggedInUserAuditorAware(UserService userService) {
        this.userService = userService;
    }

    @Override
    public @NotNull Optional<User> getCurrentAuditor() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = ((UsersDetails) userDetails).getUser();
        return userService.findById( user.getId());
    }
}
