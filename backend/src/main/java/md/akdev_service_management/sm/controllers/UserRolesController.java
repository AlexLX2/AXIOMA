package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.dto.RolesDTO;
import md.akdev_service_management.sm.dto.UserDTO;
import md.akdev_service_management.sm.dto.UserRoleDTO;
import md.akdev_service_management.sm.models.Roles;
import md.akdev_service_management.sm.models.User;
import md.akdev_service_management.sm.models.UserRole;
import md.akdev_service_management.sm.services.RoleService;
import md.akdev_service_management.sm.services.UserRoleService;
import md.akdev_service_management.sm.services.UserService;
import md.akdev_service_management.sm.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/user_role")
public class UserRolesController {
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final RoleService roleService;
    private final MappingUtils mappingUtils;

    @Autowired
    public UserRolesController(UserRoleService userRoleService, UserService userService, RoleService roleService, MappingUtils mappingUtils) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.roleService = roleService;
        this.mappingUtils = mappingUtils;
    }

    @GetMapping("get_by_username/{username}")
    public ResponseEntity<?> getRolesByUserName(@PathVariable("username") String username){

        List<UserRole> userRole = userRoleService.findByUser(userService.finByUsername(username));

        List<RolesDTO> roles = new ArrayList<>();

        userRole.forEach(i -> roles.add(mappingUtils.map(i.getRole(), RolesDTO.class)));

        return ResponseEntity.ok(Map.of(username,roles));
    }

    @GetMapping("get_by_roles/{rolename}")
    public ResponseEntity<?> getUsersByRoleName(@PathVariable("rolename") String roleName){
        List<UserRole> userRoles = userRoleService.findByRole(roleService.findByRoleName(roleName));

        List<UserDTO> users = new ArrayList<>();

        userRoles.forEach(i -> users.add(mappingUtils.map(i.getUser(),UserDTO.class)));

        return ResponseEntity.ok(Map.of(roleName,users));
    }

    @PostMapping("/new_role_user")
    public ResponseEntity<?> newUserRole(@RequestBody UserRoleDTO userRoleDTO){

       UserRole userRole  = new UserRole();
       userRole.setUser(userService.finByUsername(userRoleDTO.getUser()));
       userRole.setRole(roleService.findByRoleName(userRoleDTO.getRole()));
       userRoleService.save(userRole);

       return ResponseEntity.ok(Map.of("Role successful added: ",userRoleDTO));
    }

    @PostMapping("/delete_role_user")
    public ResponseEntity<?> deleteUserRole(@RequestBody UserRoleDTO userRoleDTO){

        User user = userService.finByUsername(userRoleDTO.getUser());
        Roles roles = roleService.findByRoleName(userRoleDTO.getRole());

        UserRole userRole = userRoleService.findByUserAndRole(user, roles);

        userRoleService.delete(userRole);
        return  ResponseEntity.ok(Map.of("Role deleted ", userRoleDTO));
    }
}
