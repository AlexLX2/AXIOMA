package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.dto.user.*;
import md.akdev_service_management.sm.models.user.Roles;
import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.models.user.UserRole;
import md.akdev_service_management.sm.services.roles.RoleService;
import md.akdev_service_management.sm.services.user.UserRoleService;
import md.akdev_service_management.sm.services.user.UserService;
import md.akdev_service_management.sm.exceptions.CstErrorResponse;
import md.akdev_service_management.sm.exceptions.DuplicateException;
import md.akdev_service_management.sm.utils.MappingUtils;
import md.akdev_service_management.sm.exceptions.NotFoundException;
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

    @GetMapping("get_all_roles")
    public ResponseEntity<?> getAllRoles(){
        List<RolesDTO> rolesDTOS = new ArrayList<>(mappingUtils.mapList(new ArrayList<>(roleService
                .getAllRoles()), RolesDTO.class));
        return ResponseEntity.ok(rolesDTOS);
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
                users.put("login", ur.getUser().getLogin());
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

    @PostMapping("/add_roles_by_single_user")
    public ResponseEntity<?> addRolesBySingleUser(@RequestBody UserRolesDTO userRolesDTO){

        User user = userService.finByUsername(userRolesDTO.getUser()).orElseThrow(NotFoundException::new);
        userRoleService.deleteByUser(user);

        for (RolesDTO role : userRolesDTO.getRole()){
            UserRole userRole = new UserRole();

            userRole.setUser(user);
            userRole.setRole(roleService.findByRoleName(role.getName()).orElseThrow(NotFoundException::new));

            try {
                userRoleService.save(userRole);
            }catch (DataIntegrityViolationException e){
                throw new DuplicateException(e.getMostSpecificCause().getMessage());
            }
        }

        return ResponseEntity.ok(Map.of("result","roles mapped to user successfully"));
    }

   @PostMapping("/add_users_by_single_role")
   public ResponseEntity<?>addUsersBySingleRole(@RequestBody UsersRoleDTO usersRoleDTO){

        Roles role = roleService.findByRoleName(usersRoleDTO.getRole()).orElseThrow(NotFoundException::new);

        userRoleService.deleteByRole(role);

        for(UserDTO userDTO: usersRoleDTO.getUsers()){
            UserRole userRole = new UserRole();

            userRole.setRole(role);
            userRole.setUser(userService.finByUsername(userDTO.getLogin()).orElseThrow(NotFoundException::new));

            try {
                userRoleService.save(userRole);
            }catch (DataIntegrityViolationException e){
                throw new DuplicateException(e.getMostSpecificCause().getMessage());
            }
        }
        return ResponseEntity.ok(Map.of("result","users mapped to role") );
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
                e.getMessage()
        );
        return new ResponseEntity<>(cstErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY );
    }
}
