package md.akdev_service_management.sm.repositories;

import md.akdev_service_management.sm.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
    Roles findRolesByName(String name);
}
