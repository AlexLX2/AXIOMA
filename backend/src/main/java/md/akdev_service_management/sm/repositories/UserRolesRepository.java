package md.akdev_service_management.sm.repositories;

import md.akdev_service_management.sm.models.Roles;
import md.akdev_service_management.sm.models.User;
import md.akdev_service_management.sm.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findUserRoleByUser(User user);

    List<UserRole> findUserRoleByRole(Roles roles);

    UserRole findUserRoleByUserAndRole(User user, Roles roles);

    void deleteByUser(User user);

    void deleteByRole(Roles roles);
}
