package md.akdev_service_management.sm.services.user;

import md.akdev_service_management.sm.models.user.Roles;
import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.models.user.UserRole;
import md.akdev_service_management.sm.repositories.user.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        public Optional<UserRole> findById(int id){
                return userRolesRepository.findById(id);
        }

        public List<UserRole> findAll(){
                return userRolesRepository.findAll();
        }

        @Transactional
        public void deleteByUser(User user){
                userRolesRepository.deleteByUser(user);
        }

        @Transactional
        public void deleteByRole(Roles role){userRolesRepository.deleteByRole(role);}
        @Transactional
        public void delete(UserRole userRole){
                userRolesRepository.delete(userRole);
        }
        @Transactional
        public void save(UserRole userRole){
                userRolesRepository.save(userRole);
        }
}
