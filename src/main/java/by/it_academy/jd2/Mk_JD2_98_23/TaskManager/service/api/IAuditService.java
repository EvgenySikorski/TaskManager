package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.PageDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface IAuditService {

    Page<Audit> get(PageRequest pageRequest);

    Audit get(UUID uuid);

    Audit save(AuditCreatDTO item);






}
