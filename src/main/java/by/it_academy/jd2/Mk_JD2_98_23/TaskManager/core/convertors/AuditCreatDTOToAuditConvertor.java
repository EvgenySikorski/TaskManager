package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Audit;

public class AuditCreatDTOToAuditConvertor {

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
