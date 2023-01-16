package md.akdev_service_management.sm.services;

import md.akdev_service_management.sm.models.Roles;
import md.akdev_service_management.sm.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Roles findByRoleName(String roleName){
     return    roleRepository.findRolesByName(roleName);
    }
}
