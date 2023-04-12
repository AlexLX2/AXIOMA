package md.akdev_service_management.sm.services.roles;

import md.akdev_service_management.sm.models.user.Roles;
import md.akdev_service_management.sm.repositories.roles.RoleRepository;
import md.akdev_service_management.sm.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Roles> findById(int id){
        return roleRepository.findById(id);
    }

    public List<Roles> findAll(){
     return  roleRepository.findAll();
    }

    @Transactional
    public void newRole(Roles roles){
        roleRepository.save(roles);
    }

    @Transactional
    public void updateRole(Roles roles){

        roleRepository.findById(roles.getId()).ifPresent(
                roleToUpdate -> {
                    roleToUpdate.setName(roles.getName());
                    roleRepository.save(roleToUpdate);
                }
        );
    }
}
