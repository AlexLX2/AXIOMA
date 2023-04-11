package md.akdev_service_management.sm.repositories.user;

import md.akdev_service_management.sm.models.user.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findRolesByName(String name);
}
