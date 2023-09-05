package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.controller;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.PageDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Audit;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuditService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/audit")

public class AuditController {

    private final IAuditService auditService;
    private final ConversionService conversionService;

    public AuditController(IAuditService auditService, ConversionService conversionService) {
        this.auditService = auditService;
        this.conversionService = conversionService;
    }

    @GetMapping(consumes = "application/json", produces = "application/json" )
    public ResponseEntity<PageDTO<AuditDTO>> page(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "dtCreate");
        Page<Audit> audit = auditService.get(pageRequest);

        PageDTO<AuditDTO> pageOfAuditDTO = new PageDTO<>(audit.getNumber(),audit.getSize(),
                audit.getTotalPages(), audit.getTotalElements(), audit.isFirst(),
                audit.getNumberOfElements(), audit.isLast(),
                audit.get().map(u -> conversionService.convert(u, AuditDTO.class)).toList());

        return new ResponseEntity<>(pageOfAuditDTO, HttpStatus.OK);
    }

    @GetMapping(value = "{uuid}", consumes = "application/json", produces = "application/json" )
    public ResponseEntity<AuditDTO> readCard(@PathVariable UUID uuid) {
        Audit audit = auditService.get(uuid);
        AuditDTO auditDTO = this.conversionService.convert(audit, AuditDTO.class);

        return new ResponseEntity<>(auditDTO, HttpStatus.OK);
    }
}
