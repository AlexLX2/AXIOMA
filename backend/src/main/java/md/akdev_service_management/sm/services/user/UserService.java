package md.akdev_service_management.sm.services.user;

import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.repositories.user.UserRepository;
import md.akdev_service_management.sm.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return   userRepository.findAll();
    }
    @Transactional
    public void newUser(User user){
        userRepository.save(user);
    }

    public Optional<User> findById(int id){
        return Optional.of(userRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    public Optional<User> finByUsername(String username){
        return  userRepository.findByLogin(username);
    }

    @Transactional
    public void update(User user){
        userRepository.findById(user.getId()).ifPresent(
                userToUpdate ->{
                    userToUpdate.setEmail(user.getEmail());
                    userToUpdate.setFirstName(user.getFirstName());
                    userToUpdate.setLastName(user.getLastName());
                    userToUpdate.setValid(user.isValid());

                    userRepository.save(userToUpdate);
                }
        );

    }

    @Transactional
    public void delete(User user){
        userRepository.findById(user.getId()).ifPresent(
                userRepository::delete
        );
    }
}
