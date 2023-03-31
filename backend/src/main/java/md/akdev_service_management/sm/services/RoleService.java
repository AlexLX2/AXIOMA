package md.akdev_service_management.sm.services;

import md.akdev_service_management.sm.models.Roles;
import md.akdev_service_management.sm.repositories.RoleRepository;
import md.akdev_service_management.sm.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = false)
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Roles> findByRoleName(String roleName){
        return Optional.ofNullable(roleRepository.findRolesByName(roleName).orElseThrow(NotFoundException::new));
    }
}
