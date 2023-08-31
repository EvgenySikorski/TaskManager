package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditUserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Audit;

public class AuditToAuditDTOConvertor {

    public AuditDTO convert(Audit source){
        return new AuditDTO(
            source.getUuid(),
            source.getDtCreate(),
            new AuditUserDTO(source.getUserUuid(),source.getMail(), source.getFio(), source.getRole()),
            source.getText(),
            source.getType(),
            source.getId()
        );
    }
}
