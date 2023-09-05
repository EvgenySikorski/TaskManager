package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditUserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Audit;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;

public class AuditToAuditDTOConvertor implements Converter<Audit, AuditDTO> {

    public AuditDTO convert(Audit source){
        return new AuditDTO(
            source.getUuid(),
            source.getDtCreate().toInstant(ZoneOffset.UTC).toEpochMilli(),
            new AuditUserDTO(source.getUserUuid(),source.getMail(), source.getFio(), source.getRole()),
            source.getText(),
            source.getType(),
            source.getId()
        );
    }
}
