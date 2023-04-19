package md.akdev_service_management.sm.controllers;


import com.fasterxml.jackson.annotation.JsonFilter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import md.akdev_service_management.sm.dto.ticket.*;
import md.akdev_service_management.sm.exceptions.AccessDeniedException;
import md.akdev_service_management.sm.exceptions.CstErrorResponse;
import md.akdev_service_management.sm.exceptions.DuplicateException;
import md.akdev_service_management.sm.exceptions.NotFoundException;
import md.akdev_service_management.sm.models.ticket.Ticket;
import md.akdev_service_management.sm.models.ticket.TicketAttachment;
import md.akdev_service_management.sm.models.ticket.TicketBody;
import md.akdev_service_management.sm.services.ticket.TicketAttachmentService;
import md.akdev_service_management.sm.services.ticket.TicketBodyService;
import md.akdev_service_management.sm.services.ticket.TicketServices;
import md.akdev_service_management.sm.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
@JsonFilter("ticketDTOFilter")
public class TicketController {
    private final TicketServices ticketServices;
    private final TicketBodyService ticketBodyService;
    private final TicketAttachmentService ticketAttachmentService;
    private final MappingUtils mappingUtils;
    String vRet;

    @Autowired
    public TicketController(TicketServices ticketServices, TicketBodyService ticketBodyService
                            , TicketAttachmentService ticketAttachmentService, MappingUtils mappingUtils) {
        this.ticketServices = ticketServices;
        this.ticketBodyService = ticketBodyService;
        this.ticketAttachmentService = ticketAttachmentService;
        this.mappingUtils = mappingUtils;
    }

    @GetMapping("/{id}")

    public ResponseEntity<?> getTicketById(@PathVariable("id") int id)  {
       TicketDTO ticketDTO;
        try {
            ticketDTO = mappingUtils.map(ticketServices.findById(id), TicketDTO.class);
        }catch (org.springframework.security.access.AccessDeniedException e){
            throw new AccessDeniedException(e.getMessage());
        }
        String[] ignorableFieldName = {"acl","fileContent","fileType"};

        MappingJacksonValue mapping = new MappingJacksonValue(ticketDTO);
        mapping.setFilters(doFilter(ignorableFieldName));
        return ResponseEntity.ok(mapping);
    }

    @GetMapping("/all")

    public ResponseEntity<?> getTicketAll(  @RequestParam(defaultValue = "0") Integer pageNo,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            @RequestParam(defaultValue = "t.ticketId") String sortBy) {

        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy).descending());
        List<Ticket> ticketPage = ticketServices.findAll(pageable).getContent();

        List<TicketDTO> ticketDTO = mappingUtils.mapList(ticketPage, TicketDTO.class);

        String[]  ignorableFieldName = {"ticketBody","acl"};
        MappingJacksonValue mapping = new MappingJacksonValue(ticketDTO);
        mapping.setFilters(doFilter(ignorableFieldName));
        return  ResponseEntity.ok(mapping);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount(){
        return ResponseEntity.ok(ticketServices.findCount());
    }

    @PostMapping("/new")
    public ResponseEntity<?> createTicket(@RequestBody TicketDTO ticketDTO) {

        Ticket ticket = mappingUtils.map(ticketDTO, Ticket.class);

        for(int i = 0; i < ticketDTO.getTicketBody().size(); i++){
            for (int j = 0; j<ticketDTO.getTicketBody().get(i).getTicketAttachment().size(); j++){
                Base64.Decoder decoder = Base64.getDecoder();

                String strAttachment = ticketDTO.getTicketBody().get(i).getTicketAttachment().get(j).getFileContent();

                byte[] decodedByte = decoder.decode(strAttachment.split(",")[1]);

                ticket.getTicketBody().get(i).getTicketAttachment().get(j).setFileContent(decodedByte);
                MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();

                ticket.getTicketBody().get(i).getTicketAttachment().get(j).setFileType(fileTypeMap.getContentType(strAttachment));
            }
        }

        if(!Objects.isNull(ticket.getTicketBody())){
            TicketBody ticketBody = new TicketBody(ticket);

            ticket.getTicketBody().stream().findFirst().ifPresent(
                    ticketBodyIf -> {

                        if(!ticketBodyIf.getBody().isBlank()){
                            ticketBody.setBody(ticketBodyIf.getBody());
                        }

                        if(!Objects.isNull(ticketBodyIf.getTicketAttachment())){
                            List<TicketAttachment> ticketAttachmentList = new ArrayList<>();
                            ticketBodyIf.getTicketAttachment().forEach(i -> {

                                TicketAttachment ticketAttachment = new TicketAttachment(ticketBody);

                                ticketAttachment.setFileType(i.getFileType());
                                ticketAttachment.setFileContent(i.getFileContent());
                                ticketAttachment.setFileName(i.getFileName());

                                ticketAttachmentList.add(ticketAttachment);

                            });
                            ticketBody.setTicketAttachment(ticketAttachmentList);


                        }

                        ticket.setTicketBody(List.of(ticketBody));
                    }

            );
        }

        ticketServices.newTicket(ticket);

        return ResponseEntity.ok(Map.of("result", "new ticket created with id - "+ ticket.getTicketId()));
    }


    @PostMapping("/new_body")
    public ResponseEntity<?>createTicketBody(@RequestBody TicketBodyDTO ticketBodyDTO){
            doBody(ticketBodyDTO);
        return ResponseEntity.ok(Map.of("result", vRet));
    }

    @PostMapping("/new_attachment/")
    public ResponseEntity<?>storeAttachments(@RequestParam("bodyId") Integer bodyId,
                                             @RequestPart("files") MultipartFile[] files) {
        ticketBodyService.getTicketBodyById(bodyId).ifPresentOrElse(
                ticketBody -> {

                        ticketAttachmentService.storeFile(files, ticketBody);
                        vRet = "attachment successfully uploaded";

                },() -> vRet = "ticket body not found"
        );

        return ResponseEntity.ok(Map.of("result", vRet));
    }

    @GetMapping("/getAttachmentByTicketBodyId/{id}")
    public List<TicketAttachmentLazyDTO> getAttachmentByTicketBodyId(@PathVariable("id") int id){
        return   mappingUtils.mapList(ticketAttachmentService.getByTicketBodyId(id), TicketAttachmentLazyDTO.class);
    }

    @GetMapping("/getAttachmentById/{id}")
    public TicketAttachmentDTO getAttachmentById(@PathVariable("id") int id){
        return mappingUtils.map(ticketAttachmentService.getById(id), TicketAttachmentDTO.class);
    }


    public FilterProvider doFilter(String[] in){
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(in);
        return new SimpleFilterProvider().addFilter("ticketDTOFilter", filter);
    }


    private void doBody(TicketBodyDTO ticketBodyDTO){

        Ticket  ticket;

        try {
            ticket = ticketServices.findById(ticketBodyDTO.getTicket());
        }catch (org.springframework.security.access.AccessDeniedException e){
            throw  new AccessDeniedException(e.getMessage());
        }

        TicketBody ticketBody = new TicketBody(ticket);

        if(!ticketBodyDTO.getBody().isBlank()){
            ticketBody.setBody(ticketBodyDTO.getBody());
        }

        if(!Objects.isNull(ticketBodyDTO.getTicketAttachment())){
            TicketAttachment ticketAttachment = new TicketAttachment(ticketBody);
            ticketBody.setTicketAttachment(List.of(ticketAttachment));
        }

        ticketBodyService.saveTicketBody(ticketBody);

        vRet = String.valueOf(ticketBody.getId());
        }



    @CrossOrigin(exposedHeaders = "Content-Disposition")
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable int id) {
        TicketAttachment ticketAttachment = ticketAttachmentService.getById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + ticketAttachment.getFileName() + "\"")
                .body(ticketAttachment.getFileContent());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({NotFoundException.class, DuplicateException.class, AccessDeniedException.class})
    private ResponseEntity<CstErrorResponse> handeException(Exception e){
        CstErrorResponse cstErrorResponse = new CstErrorResponse(
                e.getMessage()
        );
        return new ResponseEntity<>(cstErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY );
    }

}
