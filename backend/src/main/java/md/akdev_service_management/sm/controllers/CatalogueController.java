package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.dto.CatalogueDTO;
import md.akdev_service_management.sm.models.TicketCategory;
import md.akdev_service_management.sm.models.TicketPriority;
import md.akdev_service_management.sm.models.TicketStatus;
import md.akdev_service_management.sm.repositories.TicketCategoryRepository;
import md.akdev_service_management.sm.repositories.TicketPriorityRepository;
import md.akdev_service_management.sm.repositories.TicketStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RepositoryRestController
public class CatalogueController {
    @Autowired
    private TicketCategoryRepository categoryRepository;
    @Autowired
    private TicketPriorityRepository priorityRepository;
    @Autowired
    private TicketStatusRepository statusRepository;

    @GetMapping("/category")
    ResponseEntity<?> ticketCategory(PagedResourcesAssembler pagedResourcesAssembler){
        Page<TicketCategory> ticketCategories = this.categoryRepository.findAll(Pageable.ofSize(10));
        Page<CatalogueDTO> ticketCategoriesDTO = ticketCategories.map(CatalogueDTO::new);
        PagedModel<EntityModel<CatalogueDTO>> pagedModel = pagedResourcesAssembler.toModel(ticketCategoriesDTO);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/category/{id}")
     ResponseEntity<?> ticketCategoryById(@PathVariable("id") int id) {
        TicketCategory ticketCategory = this.categoryRepository.findById(id).orElseThrow();
        CatalogueDTO catalogueDTO =  new CatalogueDTO(ticketCategory);
        return ResponseEntity.ok(catalogueDTO);
    }

    @PostMapping("/category/new")
    public ResponseEntity<?> createNewTicketCategory(@RequestBody TicketCategory ticketCategory){
        this.categoryRepository.save(ticketCategory);
        return ResponseEntity.ok(Map.of("New ticket category  id = ",ticketCategory.getId()));
    }

    @GetMapping("/priority")
    ResponseEntity<?> ticketPriority(PagedResourcesAssembler pagedResourcesAssembler){
        Page<TicketPriority> ticketPriorities = this.priorityRepository.findAll(Pageable.ofSize(10));
        Page<CatalogueDTO> ticketPriorityDTO = ticketPriorities.map(CatalogueDTO::new);
        PagedModel<EntityModel<CatalogueDTO>> pagedModel = pagedResourcesAssembler.toModel(ticketPriorityDTO);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/priority/{id}")
    ResponseEntity<?> ticketPriorityById(@PathVariable("id") int id) {
        TicketPriority ticketPriority = this.priorityRepository.findById(id).orElseThrow();
        CatalogueDTO catalogueDTO =  new CatalogueDTO(ticketPriority);
        return ResponseEntity.ok(catalogueDTO);
    }

    @PostMapping("/priority/new")
    public ResponseEntity<?> createNewTicketPriority(@RequestBody TicketPriority ticketPriority){
        this.priorityRepository.save(ticketPriority);
        return ResponseEntity.ok(Map.of("new_id",ticketPriority.getId()));
    }

    @GetMapping("/status")
    ResponseEntity<?> ticketStatus(PagedResourcesAssembler pagedResourcesAssembler) {
        Page<TicketStatus> ticketStatuses = this.statusRepository.findAll(Pageable.ofSize(10));
        Page<CatalogueDTO> ticketStatusDTO = ticketStatuses.map(CatalogueDTO::new);
        PagedModel<EntityModel<CatalogueDTO>> pagedModel = pagedResourcesAssembler.toModel(ticketStatusDTO);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/status/{id}")
    ResponseEntity<?> ticketStatusById(@PathVariable("id") int id) {
        TicketStatus ticketStatus = this.statusRepository.findById(id).orElseThrow();
        CatalogueDTO catalogueDTO =  new CatalogueDTO(ticketStatus);
        return ResponseEntity.ok(catalogueDTO);
    }

    @PostMapping("/status/new")
    public ResponseEntity<?> createNewTicketStatus(@RequestBody TicketStatus ticketStatus){
        this.statusRepository.save(ticketStatus);
        return ResponseEntity.ok(Map.of("New ticket priority id = ",ticketStatus.getId()));
    }

}
