package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.dto.CatalogueDTO;
import md.akdev_service_management.sm.models.TicketCategory;
import md.akdev_service_management.sm.models.TicketPriority;
import md.akdev_service_management.sm.models.TicketStatus;
import md.akdev_service_management.sm.repositories.TicketCategoryRepository;
import md.akdev_service_management.sm.repositories.TicketPriorityRepository;
import md.akdev_service_management.sm.repositories.TicketStatusRepository;
import md.akdev_service_management.sm.utils.CstErrorResponse;
import md.akdev_service_management.sm.utils.DuplicateException;
import md.akdev_service_management.sm.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RepositoryRestController
public class CatalogueController {
    private  final TicketCategoryRepository categoryRepository;
    private final TicketPriorityRepository priorityRepository;
    private final TicketStatusRepository statusRepository;
    @Autowired
    public CatalogueController(TicketCategoryRepository categoryRepository, TicketPriorityRepository priorityRepository, TicketStatusRepository statusRepository) {
        this.categoryRepository = categoryRepository;
        this.priorityRepository = priorityRepository;
        this.statusRepository = statusRepository;
    }


    ///##################////
    @GetMapping("/category")
    ResponseEntity<?> ticketCategory(){
        List<TicketCategory> ticketCategories = this.categoryRepository.findAll();
        List<CatalogueDTO>  ticketCategoriesDTO  = ticketCategories.stream().map(CatalogueDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(ticketCategoriesDTO);
    }

    @GetMapping("/category/{id}")
    ResponseEntity<?> ticketCategoryById(@PathVariable("id") int id) {
        TicketCategory ticketCategory = this.categoryRepository.findById(id).orElseThrow(NotFoundException::new);
        CatalogueDTO catalogueDTO =  new CatalogueDTO(ticketCategory);
        return ResponseEntity.ok(catalogueDTO);
    }

    @PostMapping("/category/new")
    public ResponseEntity<?> createNewTicketCategory(@Valid @RequestBody TicketCategory ticketCategory){

        try{
            this.categoryRepository.save(ticketCategory);
        }catch(DataIntegrityViolationException e){
            throw new DuplicateException(e.getMostSpecificCause().getLocalizedMessage());
        }

        return ResponseEntity.ok(Map.of("new ticket category id",ticketCategory.getId()));
    }
    ///##################////
    @GetMapping("/priority")
    ResponseEntity<?> ticketPriority(){
        List<TicketPriority> ticketPriorities = this.priorityRepository.findAll();
        List<CatalogueDTO> catalogueDTO = ticketPriorities.stream().map(CatalogueDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(catalogueDTO);
    }

    @GetMapping("/priority/{id}")
    ResponseEntity<?> ticketPriorityById(@PathVariable("id") int id) {

        TicketPriority ticketPriority = this.priorityRepository.findById(id).orElseThrow(NotFoundException::new);
        CatalogueDTO catalogueDTO =  new CatalogueDTO(ticketPriority);
        return ResponseEntity.ok(catalogueDTO);
    }

    @PostMapping("/priority/new")
    public ResponseEntity<?> createNewTicketPriority(@RequestBody TicketPriority ticketPriority){
        try{
            this.priorityRepository.save(ticketPriority);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateException(e.getMostSpecificCause().getLocalizedMessage());
        }
        return ResponseEntity.ok(Map.of("new ticket priority id",ticketPriority.getId()));
    }
    ///##################////
    @GetMapping("/status")
    ResponseEntity<?> ticketStatus() {
        List<TicketStatus> ticketStatuses = this.statusRepository.findAll();
        List<CatalogueDTO> ticketStatusDTO = ticketStatuses.stream().map(CatalogueDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(ticketStatusDTO);
    }

    @GetMapping("/status/{id}")
    ResponseEntity<?> ticketStatusById(@PathVariable("id") int id) {
        TicketStatus ticketStatus = this.statusRepository.findById(id).orElseThrow(NotFoundException::new);
        CatalogueDTO catalogueDTO =  new CatalogueDTO(ticketStatus);
        return ResponseEntity.ok(catalogueDTO);
    }

    @PostMapping("/status/new")
    public ResponseEntity<?> createNewTicketStatus(@RequestBody TicketStatus ticketStatus){
        try{
            this.statusRepository.save(ticketStatus);
        }catch (DataIntegrityViolationException e) {
            throw new DuplicateException(e.getMostSpecificCause().getLocalizedMessage());
        }
        return ResponseEntity.ok(Map.of("new ticket status id = ",ticketStatus.getId()));
    }


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({NotFoundException.class, DuplicateException.class})
    private ResponseEntity<CstErrorResponse> handleException(Exception e){
        CstErrorResponse cstErrorResponse = new CstErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(cstErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY );
    }


}
