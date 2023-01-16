package md.akdev_service_management.sm.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import md.akdev_service_management.sm.dto.RolesDTO;
import md.akdev_service_management.sm.dto.UserDTO;
import md.akdev_service_management.sm.services.UserService;
import md.akdev_service_management.sm.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    private final UserService userService;
    private final MappingUtils mappingUtils;

    @Value("${jwt_secret}")
    private String secret;

    @Autowired
    public JWTUtil(UserService userService, MappingUtils mappingUtils) {
        this.userService = userService;
        this.mappingUtils = mappingUtils;
    }

    public String generateToken(String username){
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(120).toInstant());
        UserDTO userDTO = mappingUtils.map(userService.finByUsername(username),UserDTO.class);


        return JWT.create()
                .withSubject("User Details")
                .withClaim("userId", userDTO.getId())
                .withClaim("username", userDTO.getLogin())
                .withClaim("firstName", userDTO.getFirstName())
                .withClaim("lastName", userDTO.getLastName())
                .withClaim("email", userDTO.getEmail())
                .withClaim("isValid", userDTO.isValid())
                .withClaim("roles", userDTO.getRoles().stream().map(RolesDTO::getName).collect(Collectors.toList()))
                .withIssuedAt(new Date())
                .withIssuer("AKDev Service Manager")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));

    }

    public String validateAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("AKDev Service Manager")
                .build();

       DecodedJWT jwt =  verifier.verify(token);
       return jwt.getClaim("username").asString();
    }
}
