package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.TaskDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Audit;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Task;
import org.springframework.core.convert.converter.Converter;

public class AuditCreatDTOToAuditConvertor implements Converter<AuditCreatDTO, Audit> {

    public Audit convert(AuditCreatDTO source){
        return new Audit(
            source.getUserUuid(),
            source.getMail(),
            source.getFio(),
            source.getRole(),
            source.getText(),
            source.getType(),
            source.getId()
        );
    }
}
