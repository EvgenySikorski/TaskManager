package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IAuditDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Audit;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.AuditNotFoundException;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuditService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class AuditService implements IAuditService {

    private final IAuditDao auditDao;
    private final ConversionService conversionService;

    public AuditService(IAuditDao auditDao, ConversionService conversionService) {
        this.auditDao = auditDao;
        this.conversionService = conversionService;
    }

    @Override
    public Page<Audit> get(PageRequest pageRequest) {
        return auditDao.findAll(pageRequest);
    }

    @Override
    public Audit get(UUID uuid) {
        return auditDao.findById(uuid).orElseThrow(()-> new AuditNotFoundException(uuid));
    }

    @Override
    public Audit save(AuditCreatDTO item) {
        Audit audit = conversionService.convert(item, Audit.class);
        audit.setUuid(UUID.randomUUID());

        return auditDao.save(audit);
    }
}
