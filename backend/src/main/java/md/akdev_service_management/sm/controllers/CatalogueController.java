package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.dto.CatalogueDTO;
import md.akdev_service_management.sm.models.TicketCategory;
import md.akdev_service_management.sm.models.TicketPriority;
import md.akdev_service_management.sm.models.TicketStatus;
import md.akdev_service_management.sm.repositories.TicketCategoryRepository;
import md.akdev_service_management.sm.services.TicketCategoryService;
import md.akdev_service_management.sm.services.TicketPriorityService;
import md.akdev_service_management.sm.services.TicketStatusService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RepositoryRestController
public class CatalogueController {
    private final TicketCategoryService ticketCategoryService;
    private final TicketPriorityService priorityService;
    private final TicketStatusService ticketStatusService;

    @Autowired
    public CatalogueController(TicketCategoryService ticketCategoryService, TicketPriorityService priorityService, TicketStatusService ticketStatusService) {
        this.ticketCategoryService = ticketCategoryService;
        this.priorityService = priorityService;
        this.ticketStatusService = ticketStatusService;
    }


    ///##################////
    @GetMapping("/category")
    ResponseEntity<?> ticketCategory(){
        List<TicketCategory> ticketCategories = this.ticketCategoryService.findAll();
        List<CatalogueDTO>  ticketCategoriesDTO  = ticketCategories.stream().map(CatalogueDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(ticketCategoriesDTO);
    }

    @GetMapping("/category/{id}")
    ResponseEntity<?> ticketCategoryById(@PathVariable("id") int id) {
        TicketCategory ticketCategory = this.ticketCategoryService.findById(id).orElseThrow(NotFoundException::new);
        CatalogueDTO catalogueDTO =  new CatalogueDTO(ticketCategory);
        return ResponseEntity.ok(catalogueDTO);
    }

    @PostMapping("/category/new")
    public ResponseEntity<?> createNewTicketCategory(@Valid @RequestBody TicketCategory ticketCategory){

        try{
            this.ticketCategoryService.newTicketCategory(ticketCategory);
        }catch(DataIntegrityViolationException e){
            throw new DuplicateException(e.getMostSpecificCause().getLocalizedMessage());
        }

        return ResponseEntity.ok(Map.of("new ticket category id",ticketCategory.getId()));
    }

    @PatchMapping("/category/update")
    public ResponseEntity<?>updateTicketCategory(@Valid @RequestBody TicketCategory ticketCategory){
       if(this.ticketCategoryService.findById(ticketCategory.getId()).isPresent()){
           this.ticketCategoryService.updateTicketCategory(ticketCategory);
       }else{
           throw new NotFoundException();
       }
       return ResponseEntity.ok("update successful");
    }


    ///##################////
    @GetMapping("/priority")
    ResponseEntity<?> ticketPriority(){
        List<TicketPriority> ticketPriorities = this.priorityService.findAll();
        List<CatalogueDTO> catalogueDTO = ticketPriorities.stream().map(CatalogueDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(catalogueDTO);
    }

    @GetMapping("/priority/{id}")
    ResponseEntity<?> ticketPriorityById(@PathVariable("id") int id) {

        TicketPriority ticketPriority = this.priorityService.findById(id).orElseThrow(NotFoundException::new);
        CatalogueDTO catalogueDTO =  new CatalogueDTO(ticketPriority);
        return ResponseEntity.ok(catalogueDTO);
    }

    @PostMapping("/priority/new")
    public ResponseEntity<?> createNewTicketPriority(@RequestBody TicketPriority ticketPriority){
        try{
            this.priorityService.newTicketPriority(ticketPriority);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateException(e.getMostSpecificCause().getLocalizedMessage());
        }
        return ResponseEntity.ok(Map.of("new ticket priority id",ticketPriority.getId()));
    }

    @PatchMapping("/priority/update")
    public ResponseEntity<?> updateTicketPriority(@RequestBody TicketPriority ticketPriority){
        if(this.priorityService.findById(ticketPriority.getId()).isPresent()){
             priorityService.update(ticketPriority);
        }else {
            throw new NotFoundException();
        }

        return ResponseEntity.ok("update successful");
    }

    ///##################////
    @GetMapping("/status")
    ResponseEntity<?> ticketStatus() {
        List<TicketStatus> ticketStatuses = this.ticketStatusService.findAll();
        List<CatalogueDTO> ticketStatusDTO = ticketStatuses.stream().map(CatalogueDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(ticketStatusDTO);
    }

    @GetMapping("/status/{id}")
    ResponseEntity<?> ticketStatusById(@PathVariable("id") int id) {
        TicketStatus ticketStatus = this.ticketStatusService.findById(id).orElseThrow(NotFoundException::new);
        CatalogueDTO catalogueDTO =  new CatalogueDTO(ticketStatus);
        return ResponseEntity.ok(catalogueDTO);
    }

    @PostMapping("/status/new")
    public ResponseEntity<?> createNewTicketStatus(@RequestBody TicketStatus ticketStatus){
        try{
            this.ticketStatusService.newTicketStatus(ticketStatus);
        }catch (DataIntegrityViolationException e) {
            throw new DuplicateException(e.getMostSpecificCause().getLocalizedMessage());
        }
        return ResponseEntity.ok(Map.of("new ticket status id = ",ticketStatus.getId()));
    }

    @PatchMapping("/status/update")
    public ResponseEntity<?>updateTicketStatus(@RequestBody TicketStatus ticketStatus){
        if(ticketStatusService.findById(ticketStatus.getId()).isPresent()){
            ticketStatusService.updateTicketStatus(ticketStatus);
        }
        else {
            throw new NotFoundException();
        }
        return ResponseEntity.ok("update successful");
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
