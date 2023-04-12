package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.dto.user.RolesDTO;
import md.akdev_service_management.sm.exceptions.CstErrorResponse;
import md.akdev_service_management.sm.exceptions.DuplicateException;
import md.akdev_service_management.sm.exceptions.NotFoundException;
import md.akdev_service_management.sm.models.company.Company;
import md.akdev_service_management.sm.models.user.Roles;
import md.akdev_service_management.sm.repositories.roles.RoleRepository;
import md.akdev_service_management.sm.services.acl.AclSidService;
import md.akdev_service_management.sm.services.company.CompanyService;
import md.akdev_service_management.sm.services.roles.RoleService;
import md.akdev_service_management.sm.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;
    private final MappingUtils mappingUtils;
    private final CompanyService companyService;
    private final RoleRepository roleRepository;

    private final AclSidService aclSidService;
    private final String vPrefix = "ROLE_";
    @Autowired
    public RoleController(RoleService roleService, MappingUtils mappingUtils, CompanyService companyService,
                          RoleRepository roleRepository, AclSidService aclSidService) {
        this.roleService = roleService;
        this.mappingUtils = mappingUtils;
        this.companyService = companyService;
        this.roleRepository = roleRepository;
        this.aclSidService = aclSidService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id){
        Optional<Roles> role = Optional.ofNullable(roleService.findById(id).orElseThrow(NotFoundException::new));

     return ResponseEntity.ok(mappingUtils.map(role, RolesDTO.class));
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
      return ResponseEntity.ok(mappingUtils.mapList(roleService.findAll(), RolesDTO.class));
    }

    @PostMapping("/new")
    public ResponseEntity<?> newRole(@RequestBody @Valid RolesDTO rolesDTO){
        Company company = companyService.findById(rolesDTO.getCompany().getId()).orElseThrow(NotFoundException::new);
        Roles roles = new Roles();

        roles.setName(vPrefix + rolesDTO.getName());
        roles.setCompany(company);
        roleRepository.save(roles);
        return ResponseEntity.ok(Map.of("result", roles.getId()));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateRole( @PathVariable("id") int id
                                        ,@RequestBody @Valid  RolesDTO rolesDTO) {

        Roles oldRole = roleService.findById(id).orElseThrow(NotFoundException::new);

        Map<String, String> vRet;

        Roles roles = mappingUtils.map(rolesDTO, Roles.class);

        roles.setName(vPrefix + roles.getName());
        roles.setId(id);

        aclSidService.findBySid(oldRole.getName()).ifPresent(
                aclSidToUpdate -> {
                    aclSidToUpdate.setSid(roles.getName());
                    aclSidService.updateAcl(aclSidToUpdate);
                });

        roleService.updateRole(roles);
        vRet = Map.of("result", "update successfully");

        return ResponseEntity.ok(vRet);
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
