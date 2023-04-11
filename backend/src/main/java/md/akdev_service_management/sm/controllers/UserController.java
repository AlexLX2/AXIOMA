package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.dto.user.UserCreateDTO;
import md.akdev_service_management.sm.dto.user.UserDTO;
import md.akdev_service_management.sm.models.ticket.AdvanceInfo;
import md.akdev_service_management.sm.models.ticket.BasicInfo;
import md.akdev_service_management.sm.models.user.User;
import md.akdev_service_management.sm.services.user.UserService;
import md.akdev_service_management.sm.utils.CstErrorResponse;
import md.akdev_service_management.sm.utils.DuplicateException;
import md.akdev_service_management.sm.utils.MappingUtils;
import md.akdev_service_management.sm.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> newUser(@Validated({BasicInfo.class, AdvanceInfo.class}) @RequestBody UserCreateDTO user) {
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

    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public ResponseEntity<?> getByUserName(@PathVariable("login") String login){
        User user = userService.finByUsername(login).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(mappingUtils.map(user, UserDTO.class));
    }

    @GetMapping("/all")
    public List<UserDTO> getAll(){
        return mappingUtils.mapList(userService.findAll(),UserDTO.class);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @Validated(AdvanceInfo.class) @RequestBody UserCreateDTO userDTO){
        if (userService.findById(id).isPresent()){
            User user = mappingUtils.map(userDTO, User.class);
            user.setId(id);
            userService.update(user);
        }else {
            throw new NotFoundException();
        }

        return ResponseEntity.ok(Map.of("result","update successful"));
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id){
        User user = userService.findById(id).orElseThrow(NotFoundException::new);
        userService.delete(user);
        return ResponseEntity.ok("delete successful");
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
