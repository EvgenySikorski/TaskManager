package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDetailsImpl;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectUpdateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.UserRefDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EProjectStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IProjectDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuditService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IProjectService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {
    private static final String FIELD_NAME_NAME = "name";
    private static final String FIELD_NAME_MANAGER = "manager";
    private static final String FIELD_NAME_STAFF = "staff";
    private static final String FIELD_NAME_STATUS = "status";
    private static final String FIELD_NAME_UUID = "uuid";
    private static final String FIELD_NAME_DATEUPDATE = "dateUpdate";

    private static final String INCORRECT_DATA = "Неверные данные. Попробуйте еще раз";
    private static final String UNIQUE_CONSTRAINT_NAME = "project_name_key";
    private static final String PROJECT_EXIST_RESPONSE = "Проект с указанным названием уже сущестует";

    private final IProjectDao projectDao;
    private final IAuditService auditService;
    private final IUserService userService;
    private final UserHolder userHolder;
    private final ConversionService conversionService;

    public ProjectService(IProjectDao projectDao, IAuditService auditService,
                          IUserService userService, UserHolder userHolder, ConversionService conversionService) {
        this.projectDao = projectDao;
        this.auditService = auditService;
        this.userService = userService;
        this.userHolder = userHolder;
        this.conversionService = conversionService;
    }

    @Transactional
    @Override
    public Project save(ProjectCreateDTO item) {

        validate(item);

        UserDetailsImpl user = this.userHolder.getUser();

        if (!EUserRole.ADMIN.equals(user.getRole())){
            throw new UserAccessForbiddenException();
        }

        Project project = conversionService.convert(item, Project.class);
        Project saveProject;
        if (project != null){
            try {
                saveProject = this.projectDao.saveAndFlush(project);
                saveActionToAudit("Создан новый проект",saveProject.getUuid(), EEssenceType.PROJECT);

            } catch (DataAccessException ex) {
                if (ex.getMessage().contains(UNIQUE_CONSTRAINT_NAME)) {
                    throw new DataIntegrityViolationException(PROJECT_EXIST_RESPONSE, ex);
                } else {
                    throw new UndefinedDBEntityException(ex.getMessage(), ex);
                }
            } catch (RuntimeException ex) {
                throw new RuntimeException (ex.getMessage(), ex);
            }

        } else {
            throw new ConversionException("Ошибка конвертации из DTO в Entity");
        }

        return saveProject;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Project> get(PageRequest pageRequest, boolean archived) {
        if (pageRequest.getPageNumber() < 0 || pageRequest.getPageSize() < 1){
            throw new IncorrectDataExeption(INCORRECT_DATA);
        }
        UserDetailsImpl userDetails = this.userHolder.getUser();


        if(userDetails.getRole().equals(EUserRole.ADMIN)) {
            try{
                if (!archived) {
                    return this.projectDao.findAllByStatus(pageRequest, EProjectStatus.ACTIVE);
                }
                return this.projectDao.findAll(pageRequest);
            } catch (DataAccessException ex) {
                throw new ProjectNotFoundException("Проект не найден", ex);
            }
        } else if(userDetails.getRole().equals(EUserRole.USER)) {
            try {
                if (!archived) {
                    return this.projectDao.findByStatusAndManagerOrStatusAndStaff(
                            EProjectStatus.ACTIVE, userDetails.getUuid(),
                            EProjectStatus.ACTIVE, userDetails.getUuid(),
                            pageRequest);
                }
                return this.projectDao.findByManagerOrStaff(
                        userDetails.getUuid(),
                        userDetails.getUuid(),
                        pageRequest);
            } catch (DataAccessException ex) {
                throw new ProjectNotFoundException("Проект не найден", ex);
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Project get(UUID uuid) {
        if (!this.projectDao.existsById(uuid)){
            throw new ProjectNotFoundException("Проект c указанным UUID " + uuid + " не найден");
        }

        UserDetailsImpl user = this.userHolder.getUser();
        if (EUserRole.ADMIN.equals(user.getRole())){
            return this.projectDao.getReferenceById(uuid);
        }

        Project project = this.projectDao.getReferenceById(uuid);
        Set<UUID> staff = project.getStaff();
        staff.add(project.getManager());

        if (! staff.contains(user.getUuid())){
            throw new UserAccessForbiddenException();
        }

        return project;
    }

    @Transactional
    @Override
    public Project update(ProjectUpdateDTO item) {

        validate(item);

        Project projectFromDB = this.get(item.getUuid());

        UserDetailsImpl user = this.userHolder.getUser();

        if (!(EUserRole.MANAGER.equals(user.getRole()) || EUserRole.ADMIN.equals(user.getRole()))){
            throw new UserAccessForbiddenException();
        }

        if (EUserRole.MANAGER.equals(user.getRole())){
            if (!user.getUuid().equals(projectFromDB.getManager())){
                throw new UserAccessForbiddenException();
            }
        }

        if (!item.getDateUpdate().isEqual(projectFromDB.getDtUpdate())){
            throw new VersionException("Версии не совпадают");
        }

        projectFromDB.setName(item.getName());
        projectFromDB.setDescription(item.getDescription());
        projectFromDB.setStatus(item.getStatus());
        projectFromDB.setManager(item.getManager().getUuid());

        Set<UUID> setUserRef = new HashSet<>();
        for (UserRefDTO staff : item.getStaff()) {
            setUserRef.add(staff.getUuid());
            projectFromDB.setStaff(setUserRef);
        }
        try {
            Project updateProject = this.projectDao.saveAndFlush(projectFromDB);
            saveActionToAudit("Обновлен проект", updateProject.getUuid(), EEssenceType.PROJECT);
            return updateProject;
        } catch (DataAccessException ex) {
            if (ex.getMessage().contains(UNIQUE_CONSTRAINT_NAME)) {
                throw new DataIntegrityViolationException(PROJECT_EXIST_RESPONSE, ex);
            } else {
                throw new UndefinedDBEntityException(ex.getMessage(), ex);
            }
        } catch (RuntimeException ex) {
            throw new RuntimeException (ex.getMessage(), ex);
        }
    }

    private void saveActionToAudit(String text, UUID uuid, EEssenceType essenceType){
        UserDetailsImpl userDetails = userHolder.getUser();
        AuditCreatDTO auditCreatDTO = new AuditCreatDTO(
                userDetails.getUuid(),
                userDetails.getUsername(),
                userDetails.getFio(),
                userDetails.getRole(),
                text,
                essenceType,
                uuid.toString()
        );
        this.auditService.save(auditCreatDTO);
    }

    @Override
    public List<Project> getByUser(UUID userUuid) {
        List<Project> projectList = this.projectDao.findByManagerOrStaff(userUuid, userUuid);
        if (projectList.isEmpty()){
            throw new ProjectNotFoundException("Проектов, доступных пользователю не найдено");
        }
        return projectList;
    }

    @Override
    public List<Project> get(List<UUID> projectUuids, UUID user) {
        return this.projectDao.findByUuidInAndManagerOrUuidInAndStaff(
                projectUuids, user,
                projectUuids, user);
    }

    @Override
    public List<Project> findAllByIdIn(Collection<UUID> uuid) {
        return this.projectDao.findAllById(uuid);    }

    private void validate(ProjectCreateDTO item){
        Map<String, String> errors = new HashMap<>();

        String name = item.getName();
        if (name == null || "".equals(name)){
            errors.put(FIELD_NAME_NAME, "Имя проекта не указано");
        }

        UserRefDTO manager = item.getManager();
        if (manager == null){
            errors.put(FIELD_NAME_MANAGER, "Менеджер проекта не указан");
        }

        if (manager != null){
            if (manager.getUuid() == null){
                errors.put(FIELD_NAME_MANAGER, "Менеджер проекта не указан");
            }
            if (userService.get(manager.getUuid()) == null){
                throw new UserNotFoundException("Пользователь, назначенный менеджером проекта, с UUID " + manager.getUuid() + " не существует");
            }
        }

        Set<UserRefDTO> staff = item.getStaff();
        if (staff == null){
            errors.put(FIELD_NAME_STAFF, "Исполнитель не указан");
        } else {
            Set<UUID> staffUuid = staff.stream().map(UserRefDTO::getUuid).collect(Collectors.toSet());
            List<User> userList = userService.findAllById(staffUuid);
            if (userList.size() != staffUuid.size()){
                throw new UserNotFoundException("Один из пользователей, назначенный исполнителем проекта не существует");
            }
        }

        EProjectStatus projectStatus = item.getStatus();
        if (projectStatus == null || "".equals(projectStatus.name())){
            errors.put(FIELD_NAME_STATUS, "Статус не указан");
        } else{
            EProjectStatus[] arrProjectStatus = EProjectStatus.values();
            if (!Arrays.asList(arrProjectStatus).contains(projectStatus)){
                errors.put(FIELD_NAME_STATUS, "Статус проекта указан не верно");
            }
        }
        if (!errors.isEmpty()) {
            throw new NotValidBodyException(errors);
        }
    }

    private void validate(ProjectUpdateDTO item){
        Map<String, String> errors = new HashMap<>();

        String name = item.getName();
        if (name == null || "".equals(name)){
            errors.put(FIELD_NAME_NAME, "Имя проекта не указано");
        }

        UserRefDTO manager = item.getManager();
        if (manager == null){
            errors.put(FIELD_NAME_MANAGER, "Менеджер проекта не указан");
        }

        if (manager != null){
            if (manager.getUuid() == null){
                errors.put(FIELD_NAME_MANAGER, "Менеджер проекта не указан");
            }
            if (userService.get(manager.getUuid()) == null){
                throw new UserNotFoundException("Пользователь, назначенный менеджером проекта, с UUID " + manager.getUuid() + " не существует");
            }
        }

        Set<UserRefDTO> staff = item.getStaff();
        if (staff == null){
            errors.put(FIELD_NAME_STAFF, "Исполнитель не указан");
        } else {
            Set<UUID> staffUuid = staff.stream().map(UserRefDTO::getUuid).collect(Collectors.toSet());
            List<User> userList = userService.findAllById(staffUuid);
            if (userList.size() != staffUuid.size()){
                throw new UserNotFoundException("Один из пользователей, назначенный исполнителем проекта не существует");
            }
        }

        EProjectStatus projectStatus = item.getStatus();
        if (projectStatus == null || "".equals(projectStatus.name())){
            errors.put(FIELD_NAME_STATUS, "Статус не указан");
        } else{
            EProjectStatus[] arrProjectStatus = EProjectStatus.values();
            if (!Arrays.asList(arrProjectStatus).contains(projectStatus)){
                errors.put(FIELD_NAME_STATUS, "Статус проекта указан не верно");
            }
        }

        UUID uuid = item.getUuid();
        if (uuid.toString() == null || "".equals(uuid.toString())){
            errors.put(FIELD_NAME_UUID, "UUID пользователя не указан");
        }

        LocalDateTime dateUpdate = item.getDateUpdate();
        if (dateUpdate == null){
            errors.put(FIELD_NAME_DATEUPDATE, "Дата обновления проекта не указана");
        }
        if (!errors.isEmpty()) {
            throw new NotValidBodyException(errors);
        }
    }
}


