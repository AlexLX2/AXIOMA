package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.dto.UserCreateDTO;
import md.akdev_service_management.sm.dto.UserDTO;
import md.akdev_service_management.sm.models.User;
import md.akdev_service_management.sm.services.UserService;
import md.akdev_service_management.sm.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<UserCreateDTO> newTicket(@Valid @RequestBody UserCreateDTO user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.newUser(mappingUtils.map(user,User.class));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<UserDTO> getTicketAll(){
        return mappingUtils.mapList(userService.findAll(),UserDTO.class);
    }



}
