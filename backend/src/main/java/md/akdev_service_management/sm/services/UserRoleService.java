package md.akdev_service_management.sm.services;

import md.akdev_service_management.sm.models.Roles;
import md.akdev_service_management.sm.models.User;
import md.akdev_service_management.sm.models.UserRole;
import md.akdev_service_management.sm.repositories.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class UserRoleService {
        private final UserRolesRepository userRolesRepository;

        @Autowired
        public UserRoleService(UserRolesRepository userRolesRepository) {
                this.userRolesRepository = userRolesRepository;
        }

        public List<UserRole> findByUser(User user){
                return userRolesRepository.findUserRoleByUser(user);
        }

        public List<UserRole> findByRole(Roles roles){
                return userRolesRepository.findUserRoleByRole(roles);
        }

        public UserRole findByUserAndRole(User user, Roles roles){
                return userRolesRepository.findUserRoleByUserAndRole(user, roles);
        }

        @Transactional
        public void delete(UserRole userRole){
                userRolesRepository.delete(userRole);
        }
        @Transactional
        public void save(UserRole userRole){
                userRolesRepository.save(userRole);
        }
}
