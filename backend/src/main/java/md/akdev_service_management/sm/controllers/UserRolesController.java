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
import md.akdev_service_management.sm.utils.CstErrorResponse;
import md.akdev_service_management.sm.utils.DuplicateException;
import md.akdev_service_management.sm.utils.MappingUtils;
import md.akdev_service_management.sm.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
        User user = userService.finByUsername(username).orElseThrow(NullPointerException::new);

        List<UserRole> userRole = userRoleService.findByUser(user);
        List<RolesDTO> roles = new ArrayList<>(mappingUtils.mapList(userRole.stream().map(UserRole::getRole).collect(Collectors.toList()), RolesDTO.class));

        return ResponseEntity.ok(Map.of(user.getId(),roles));
    }

    @GetMapping("get_by_roles/{rolename}")
    public ResponseEntity<?> getUsersByRoleName(@PathVariable("rolename") String roleName){

        Roles roles = roleService.findByRoleName(roleName).orElseThrow(NotFoundException::new);
        List<UserRole> userRoles = userRoleService.findByRole(roles);

        List<Map<?,?>> finUser = new ArrayList<>();
        for(UserRole ur: userRoles){
            Map<String, String> users = new HashMap<>();
                users.put("id", String.valueOf(ur.getUser().getId()));
                users.put("username", ur.getUser().getLogin());
          finUser.add(users);
        }

        return ResponseEntity.ok(Map.of(roleName,finUser));
    }

    @PostMapping("/new_role_user")
    public ResponseEntity<?> newUserRole(@RequestBody UserRoleDTO userRoleDTO){

        UserRole userRole  = new UserRole();

        userRole.setUser(userService.finByUsername(userRoleDTO.getUser()).orElseThrow(NotFoundException::new));
        userRole.setRole(roleService.findByRoleName(userRoleDTO.getRole()).orElseThrow(NotFoundException::new));

        try {
            userRoleService.save(userRole);
        }catch(DataIntegrityViolationException e){
            throw new DuplicateException(e.getMostSpecificCause().getLocalizedMessage());
        }

        return ResponseEntity.ok(Map.of("role to user",userRoleDTO.getRole() + " to " + userRoleDTO.getUser()));
    }

    @PostMapping("/delete_role_user/{id}")
    public ResponseEntity<?> deleteUserRole(@PathVariable("id") int id){

        UserRole userRole = userRoleService.findById(id).orElseThrow(NotFoundException::new);

        userRoleService.delete(userRole);
        return  ResponseEntity.ok(Map.of("role deleted", userRole.getUser().getLogin() + "<>" + userRole.getRole().getName()));
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({NotFoundException.class, DuplicateException.class})
    private ResponseEntity<CstErrorResponse> handeException(Exception e){
        CstErrorResponse cstErrorResponse = new CstErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(cstErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY );
    }
}
