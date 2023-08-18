package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserToUserDTOConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectUpdateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.UserRefDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IAuditDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IProjectDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.ConversionException;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuditService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IProjectService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Service
public class ProjectService implements IProjectService {

    private final IProjectDao projectDao;
    private final IUserService userService;
    private final IAuditService auditService;
    private final UserHolder userHolder;
    private final ConversionService conversionService;

    public ProjectService(IProjectDao projectDao, IUserService userService,
                          IAuditService auditService, UserHolder userHolder,
                          ConversionService conversionService) {
        this.projectDao = projectDao;
        this.userService = userService;
        this.auditService = auditService;
        this.userHolder = userHolder;
        this.conversionService = conversionService;
    }

    @Transactional
    @Override
    public Project save(ProjectCreatDTO item) {

        Project project = conversionService.convert(item, Project.class);
        Project saveProject;
        if (project != null){
            saveProject = this.projectDao.saveAndFlush(project);
        } else {
            throw new ConversionException("Ошибка конвертации из DTO в Entity");
        }

        saveActionToAudit("Создан новый проект",saveProject.getUuid().toString(), EEssenceType.PROJECT);
        return saveProject;
    }

    @Override
    public Page<Project> get(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Project get(UUID uuid) {
        return null;
    }

    @Override
    public Project update(ProjectUpdateDTO item) {
        return null;
    }

    private void saveActionToAudit(String text, String id, EEssenceType essenceType){
        String userEmail = userHolder.getUser().getUsername();
        User user = userService.getCardByMail(userEmail);
        AuditCreatDTO auditCreatDTO = new AuditCreatDTO(
                user.getUuid(),
                user.getMail(),
                user.getFio(),
                user.getRole(),
                text,
                essenceType,
                id
        );
        this.auditService.save(auditCreatDTO);
    }
}
