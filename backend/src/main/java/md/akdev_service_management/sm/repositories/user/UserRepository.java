package md.akdev_service_management.sm.repositories.user;

import md.akdev_service_management.sm.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByLogin(String username);
}
