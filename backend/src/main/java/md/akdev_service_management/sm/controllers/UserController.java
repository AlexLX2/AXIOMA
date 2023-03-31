package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.dto.UserCreateDTO;
import md.akdev_service_management.sm.dto.UserDTO;
import md.akdev_service_management.sm.models.User;
import md.akdev_service_management.sm.services.UserService;
import md.akdev_service_management.sm.utils.CstErrorResponse;
import md.akdev_service_management.sm.utils.DuplicateException;
import md.akdev_service_management.sm.utils.MappingUtils;
import md.akdev_service_management.sm.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final MappingUtils mappingUtils;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserController(UserService userService, MappingUtils mappingUtils, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.mappingUtils = mappingUtils;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/new")

    public ResponseEntity<?> newUser(@Valid @RequestBody UserCreateDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userService.newUser(mappingUtils.map(user,User.class));
        }catch (DataIntegrityViolationException e){
            throw new DuplicateException(e.getMostSpecificCause().getLocalizedMessage());
        }
        return  ResponseEntity.ok(Map.of("result", "User " + user.getFirstName() + " " + user.getLastName() + " successful created."));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        User user = userService.findById(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(mappingUtils.map(user, UserDTO.class));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUserName(@PathVariable("username") String username){
        User user = userService.finByUsername(username).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(mappingUtils.map(user, UserDTO.class));
    }

    @GetMapping("/all")
    public List<UserDTO> getAll(){
        return mappingUtils.mapList(userService.findAll(),UserDTO.class);
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
