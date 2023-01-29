package md.akdev_service_management.sm.controllers;

import md.akdev_service_management.sm.dto.*;
import md.akdev_service_management.sm.models.*;
import md.akdev_service_management.sm.security.IAuthenticationFacade;
import md.akdev_service_management.sm.services.*;
import md.akdev_service_management.sm.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketServices ticketServices;
    private final TicketBodyService ticketBodyService;
    private final TicketAttachmentService ticketAttachmentService;
    private final MappingUtils mappingUtils;
    private final IAuthenticationFacade authenticationFacade;


    @Autowired
    public TicketController(TicketServices ticketServices, TicketBodyService ticketBodyService
                            , TicketAttachmentService ticketAttachmentService, MappingUtils mappingUtils, IAuthenticationFacade authenticationFacade) {
        this.ticketServices = ticketServices;
        this.ticketBodyService = ticketBodyService;
        this.ticketAttachmentService = ticketAttachmentService;
        this.mappingUtils = mappingUtils;
        this.authenticationFacade = authenticationFacade;
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/{id}")
    public TicketLazyDTO getTicketById(@PathVariable("id") int id) {
        return mappingUtils.map(ticketServices.findById(id), TicketLazyDTO.class);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getTicketAll(  @RequestParam(defaultValue = "0") Integer pageNo,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(defaultValue = "t.ticketId") String sortBy){

        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy).descending());
        List<Ticket> ticketPage = ticketServices.findAll(pageable).getContent();


        return  ResponseEntity.ok(mappingUtils.mapList(ticketPage, TicketHeaderDTO.class));
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount(){
        return ResponseEntity.ok(ticketServices.findCount());
    }

    @PostMapping("/createTicket")
    public ResponseEntity<Map<String,Integer>> createTicket(){
       return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/createTicketHeader")
    public ResponseEntity<Map<String,Integer>> createTicketHeader(@RequestBody Ticket ticket){

        ticketServices.newTicket(ticket);
        return ResponseEntity.ok(Map.of("ticketId", ticket.getTicketId()));
    }

    @PostMapping("/createTicketBody")
    public ResponseEntity<?>createTicketBody(@RequestBody TicketBody ticketBody){

        ticketBodyService.saveTicketBody(ticketBody);
        return ResponseEntity.ok(Map.of("ticketBodyId", ticketBody.getId()));
    }

    @PostMapping("/createAttachment")
    public ResponseEntity<Map<String,Integer>>storeAttachments(@RequestParam("ticketBodyId") int id,
                                                               @RequestPart("files") MultipartFile[] files) {

        TicketBody ticketBody = ticketBodyService.getTicketBodyById(id).orElse(new TicketBody());
        User user = ticketBodyService.getTicketBodyById(id).stream().findAny().orElseThrow().getCreatedBy(); //ToDo убрать
        ticketAttachmentService.storeFile(files,ticketBody,user);

        return ResponseEntity.ok(Map.of("attachmentAllOk", 1));
    }

    @GetMapping("/getAttachmentByTicketBodyId/{id}")
    public List<TicketAttachmentLazyDTO> getAttachmentByTicketBodyId(@PathVariable("id") int id){
        return   mappingUtils.mapList(ticketAttachmentService.getByTicketBodyId(id), TicketAttachmentLazyDTO.class);
    }

    @GetMapping("/getAttachmentById/{id}")
    public TicketAttachmentDTO getAttachmentById(@PathVariable("id") int id){
        return mappingUtils.map(ticketAttachmentService.getById(id), TicketAttachmentDTO.class);
    }

    @CrossOrigin(exposedHeaders = "Content-Disposition")
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable int id) {
        TicketAttachment ticketAttachment = ticketAttachmentService.getById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + ticketAttachment.getFileName() + "\"")
                .body(ticketAttachment.getFileContent());
    }

}
