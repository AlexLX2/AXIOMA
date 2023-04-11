package md.akdev_service_management.sm.services.user;

import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.repositories.user.UserRepository;
import md.akdev_service_management.sm.security.UsersDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UsersDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);

        if(user.isEmpty()){
            throw  new UsernameNotFoundException("User not found");
        }

        return new UsersDetails(user.get());
    }
}
