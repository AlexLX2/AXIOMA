package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.dto.company.CompanyDTO;
import md.akdev_service_management.sm.models.company.Company;
import md.akdev_service_management.sm.services.company.CompanyService;
import md.akdev_service_management.sm.exceptions.CstErrorResponse;
import md.akdev_service_management.sm.exceptions.DuplicateException;
import md.akdev_service_management.sm.utils.MappingUtils;
import md.akdev_service_management.sm.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    private final MappingUtils mappingUtils;

    @Autowired
    public CompanyController(CompanyService companyService,MappingUtils mappingUtils) {
        this.companyService = companyService;
        this.mappingUtils = mappingUtils;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<CompanyDTO> companyDTO = mappingUtils.mapList(companyService.findAll(), CompanyDTO.class);

        return ResponseEntity.ok(companyDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>findById(@PathVariable("id") int id) {

       Company company =  companyService.findById(id).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(mappingUtils.map(company, CompanyDTO.class));
    }

    @PostMapping("/new")
    public ResponseEntity<?>newCompany(@RequestBody @Valid CompanyDTO companyDTO){

        Company company =  mappingUtils.map(companyDTO, Company.class);
        companyService.newCompany(company);
        return ResponseEntity.ok(Map.of("result", "new company successfully created with id - " +company.getId()));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?>updateCompany(@PathVariable("id") int id, @RequestBody CompanyDTO companyDTO){
        Map<String, String> vRet = Map.of("result", "update failed");

        if(companyService.findById(id).isPresent()){

            Company company = mappingUtils.map(companyDTO, Company.class);
            company.setId(id);

            companyService.updateCompany(company);
            vRet = Map.of("result", "update successfully");
        }
        return    ResponseEntity.ok(vRet);
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
