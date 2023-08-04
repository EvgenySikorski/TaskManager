package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.config;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.AuditCreatDTOToAuditConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.AuditToAuditDTOConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserDTOToUserConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserToUserDTOConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IAuditDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.AuditService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuditService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

@Configuration
public class AuditServiceConfig {

    @Bean
    public AuditCreatDTOToAuditConvertor auditCreatDTOToAuditConvertor(){
        return new AuditCreatDTOToAuditConvertor();
    }
    @Bean
    public AuditToAuditDTOConvertor auditToAuditDTOConvertor(){
        return new AuditToAuditDTOConvertor();
    }
    @Bean
    public IAuditService auditService(IAuditDao auditDao, AuditCreatDTOToAuditConvertor convertor){
        return new AuditService(auditDao, convertor);
    }

}
