package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDetailsImpl;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.ETaskStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.ITaskDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Task;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuditService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IProjectService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.ITaskService;
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
@Service
public class TaskService implements ITaskService {

    private static final String PARAM_NAME_PROJECT = "project";
    private static final String PARAM_NAME_TITLE = "title";
    private static final String PARAM_NAME_IMPLEMENTER = "implementer";
    private static final String INCORRECT_DATA = "Неверные данные. Попробуйте еще раз";
    private static final String TASK_EXIST_RESPONSE = "Задача с указанным названием уже сущестует";
    private static final String UNIQUE_CONSTRAINT_TITLE = "task_title_key";

    private final ITaskDao taskDao;
    private final IProjectService projectService;
    private final UserHolder userHolder;
    private final IUserService userService;
    private final IAuditService auditService;
    private final ConversionService conversionService;

    public TaskService(ITaskDao taskDao, IProjectService projectService,
                       UserHolder userHolder, IUserService userService, IAuditService auditService, ConversionService conversionService) {
        this.taskDao = taskDao;
        this.projectService = projectService;
        this.userHolder = userHolder;
        this.userService = userService;
        this.auditService = auditService;
        this.conversionService = conversionService;
    }

    @Transactional
    @Override
    public Task save(TaskCreateDTO item) {

        validateDto(item);

        Project project = this.projectService.get(item.getProject().getUuid());

        Task task = new Task();

        task.setUuid(UUID.randomUUID());
        task.setProject(project);
        task.setTitle(item.getTitle());
        task.setDescription(item.getDescription());
        task.setStatus(item.getStatus());
        task.setImplementer(item.getImplementer().getUuid());

        Task saveTask;
        try {
            saveTask = this.taskDao.saveAndFlush(task);
        } catch (DataAccessException ex) {
            if (ex.getMessage().contains(UNIQUE_CONSTRAINT_TITLE)) {
                throw new DataIntegrityViolationException(TASK_EXIST_RESPONSE, ex);
            } else {
                throw new UndefinedDBEntityException(ex.getMessage(), ex);
            }
        } catch (RuntimeException ex) {
            throw new RuntimeException (ex.getMessage(), ex);
        }
        saveActionToAudit("Создана новая задача",saveTask.getUuid(), EEssenceType.TASK);

        return saveTask;
    }

    public Page<Task> get(PageRequest pageRequest, TaskFilterDTO taskFilterDTO) {
        if (pageRequest.getPageNumber() < 0 || pageRequest.getPageSize() < 1){
            throw new IncorrectDataExeption(INCORRECT_DATA);
        }

        UserDetailsImpl userDetails = this.userHolder.getUser();

        if (userDetails.getRole().equals(EUserRole.ADMIN)){
            return findPageForAdmin(taskFilterDTO, pageRequest);
        }

        if (userDetails.getRole().equals(EUserRole.USER)){
            return findPageForUser(userDetails, taskFilterDTO, pageRequest);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Task get(UUID uuid) {
        if (!this.taskDao.existsById(uuid)) {
            throw new TaskNotFoundException (uuid);
        }
        UserDetailsImpl userDetails = userHolder.getUser();
        if (userDetails.getRole().equals(EUserRole.ADMIN)){
            return taskDao.findById(uuid).orElseThrow(() -> new TaskNotFoundException(uuid));
        } else{
            List<Project> projectList = this.projectService.getByUser(userDetails.getUuid());
            List<UUID> projectUuid = projectList.stream().map(Project::getUuid).toList();
            return taskDao.findByUuidAndProjectIn(uuid, projectUuid).orElseThrow(()-> new UserAccessForbiddenException());

        }
    }

    @Override
    public Task update(TaskCreateDTO item, UUID uuid, LocalDateTime dtUpdate) {
        validateDto(item);

        Task taskFromDB = get(uuid);

        if (!dtUpdate.isEqual(taskFromDB.getDtUpdate())){
            throw new VersionException("Версии не совпадают");
        }

        taskFromDB.setProject(this.projectService.get(item.getProject().getUuid()));
        taskFromDB.setTitle(item.getTitle());
        taskFromDB.setDescription(item.getDescription());
        taskFromDB.setStatus(item.getStatus());
        taskFromDB.setImplementer(item.getImplementer().getUuid());
        Task updateTask;
        try {
            updateTask = this.taskDao.saveAndFlush(taskFromDB);
        } catch (DataAccessException ex) {
            if (ex.getMessage().contains(UNIQUE_CONSTRAINT_TITLE)) {
                throw new DataIntegrityViolationException(TASK_EXIST_RESPONSE, ex);
            } else {
                throw new UndefinedDBEntityException(ex.getMessage(), ex);
            }
        } catch (RuntimeException ex) {
            throw new RuntimeException (ex.getMessage(), ex);
        }
        saveActionToAudit("Задача обновлена",updateTask.getUuid(), EEssenceType.TASK);

        return updateTask;
    }

    @Override
    public Task updateStatus(TaskStatusUpdateDTO item) {
        Task taskFromDB = taskDao.findById(item.getUuid())
                .orElseThrow(() -> new TaskNotFoundException(item.getUuid()));

        if (!item.getDateUpdate().isEqual(taskFromDB.getDtUpdate())){
            throw new VersionException("Версии не совпадают");
        }
        taskFromDB.setStatus(item.getStatus());
        Task updateTask;
        try {
            updateTask = this.taskDao.saveAndFlush(taskFromDB);
        } catch (DataAccessException ex) {
            if (ex.getMessage().contains(UNIQUE_CONSTRAINT_TITLE)) {
                throw new DataIntegrityViolationException(TASK_EXIST_RESPONSE, ex);
            } else {
                throw new UndefinedDBEntityException(ex.getMessage(), ex);
            }
        } catch (RuntimeException ex) {
            throw new RuntimeException (ex.getMessage(), ex);
        }
        saveActionToAudit("Статус задачи обновлена",updateTask.getUuid(), EEssenceType.TASK);

        return updateTask;
    }

    private void validateDto(TaskCreateDTO dto){
        Map<String, String> errors = new HashMap<>();

        ProjectRefDTO projectRefDTO = dto.getProject();
        if (projectRefDTO == null){
            errors.put(PARAM_NAME_PROJECT, "Project not specified");
        } else if (projectRefDTO.getUuid() == null){
            errors.put(PARAM_NAME_PROJECT, "UUID of project contains uncorrect data");
        }

        String title = dto.getTitle();
        if (title == null){
            errors.put(PARAM_NAME_TITLE, "Title not specified");
        } else if (title.equals("")){
            errors.put(PARAM_NAME_TITLE, "Title cannot be empty");
        }

        if (!errors.isEmpty()){
            throw new NotValidTaskCreatDtoBodyException(errors);
        }

        checkAccessToCreatTask(projectRefDTO.getUuid());

        UserRefDTO implementer = dto.getImplementer();
        if (implementer == null){
            errors.put(PARAM_NAME_IMPLEMENTER, "Implementer not specified");
        } else if (implementer.getUuid() == null){
            errors.put(PARAM_NAME_IMPLEMENTER, "UUID of implementer contains uncorrect data");
        }

        try{
            if (dto.getImplementer() != null){
                Project project = this.projectService.get(dto.getProject().getUuid());
                Set<UUID> participant = project.getStaff();
                participant.add(project.getManager());
                if (!participant.contains(dto.getImplementer().getUuid())){
                    errors.put(PARAM_NAME_IMPLEMENTER, "Such user does not participate in this project");
                }
            }
        } catch(ProjectNotFoundException ex){
            errors.put(PARAM_NAME_PROJECT, "Such project does not exists");
        }

        if (!errors.isEmpty()){
            throw new NotValidTaskCreatDtoBodyException(errors);
        }
    }

    private void checkAccessToCreatTask(UUID projectRef){
        Project project = this.projectService.get(projectRef);
        Set<UUID> participant = project.getStaff();
        participant.add(project.getManager());

        UserDetailsImpl user = this.userHolder.getUser();
        if (!EUserRole.ADMIN.equals(user.getRole()) && !participant.contains(user.getUuid())){
            throw new UserAccessForbiddenException();
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

    private Page<Task> findPageForAdmin (TaskFilterDTO taskFilterDTO, PageRequest pageRequest){

        if (taskFilterDTO.getStatus() == null){
            if (taskFilterDTO.getProject() == null && taskFilterDTO.getImplementer() == null) {
                return this.taskDao.findAll(pageRequest);
            } else if(taskFilterDTO.getProject() == null){
                return this.taskDao.findByImplementerIn(
                        taskFilterDTO.getImplementer(),
                        pageRequest);
            } else if (taskFilterDTO.getImplementer() == null) {
                return this.taskDao.findByProjectUuidIn(
                        taskFilterDTO.getProject(),
                        pageRequest);
            } else {
                return taskDao.findByProjectUuidInAndImplementerIn(
                        taskFilterDTO.getProject(),
                        taskFilterDTO.getImplementer(),
                        pageRequest
                );
            }
        } else {
            if (taskFilterDTO.getProject() == null && taskFilterDTO.getImplementer() == null) {
                return this.taskDao.findByStatusIn(
                        taskFilterDTO.getStatus(),
                        pageRequest);
            } else if(taskFilterDTO.getProject() == null){
                return this.taskDao.findByImplementerInAndStatusIn(
                        taskFilterDTO.getImplementer(),
                        taskFilterDTO.getStatus(),
                        pageRequest);
            } else if (taskFilterDTO.getImplementer() == null) {
                return this.taskDao.findByProjectUuidInAndStatusIn(
                        taskFilterDTO.getProject(),
                        taskFilterDTO.getStatus(),
                        pageRequest);
            } else {
                return taskDao.findByProjectUuidInAndStatusInAndImplementerIn(
                        taskFilterDTO.getProject(),
                        taskFilterDTO.getStatus(),
                        taskFilterDTO.getImplementer(),
                        pageRequest
                );
            }
        }
    }

    private Page<Task> findPageForUser (UserDetailsImpl userDetails, TaskFilterDTO taskFilterDTO, PageRequest pageRequest){
        List<Project> projectList = getFilteredProjects(userDetails,taskFilterDTO);

        if (taskFilterDTO.getImplementer() == null){
            taskFilterDTO.setImplementer(getProjectParticipants(projectList, taskFilterDTO));
        }

        if (taskFilterDTO.getStatus() == null) {
            if (taskFilterDTO.getProject() == null) {
                return this.taskDao.findByImplementerIn(
                        taskFilterDTO.getImplementer(),
                        pageRequest);
            } else {
                return this.taskDao.findByProjectUuidInAndImplementerIn(
                        taskFilterDTO.getProject(),
                        taskFilterDTO.getImplementer(),
                        pageRequest);
            }
        } else {
            if (taskFilterDTO.getProject() == null) {
                return this.taskDao.findByImplementerInAndStatusIn(
                        taskFilterDTO.getImplementer(),
                        taskFilterDTO.getStatus(),
                        pageRequest);
            } else {
                return this.taskDao.findByProjectUuidInAndStatusInAndImplementerIn(
                        taskFilterDTO.getProject(),
                        taskFilterDTO.getStatus(),
                        taskFilterDTO.getImplementer(),
                        pageRequest);
            }
        }
    }

    private List<Project> getFilteredProjects(UserDetailsImpl userDetails, TaskFilterDTO taskFilterDTO){
        List<Project> projectList;
        if (taskFilterDTO.getProject() == null){
            projectList = this.projectService.getByUser(userDetails.getUuid());
            taskFilterDTO.setProject(projectList.stream().map(Project::getUuid).toList());
        } else{
            projectList = this.projectService.get(taskFilterDTO.getProject(), userDetails.getUuid());
            List<UUID> projects = new ArrayList<>();
            for (Project project : projectList) {
                projects.add(project.getUuid());
            }
            taskFilterDTO.setProject(projects);
        }
        return projectList;
    }

    private List<UUID> getProjectParticipants(List<Project> projectList, TaskFilterDTO taskFilterDTO){
        Set<UUID> participants = new HashSet<>();
        for (Project project : projectList) {
            for (UUID staff : project.getStaff()){
                participants.add(staff);
            }
            participants.add(project.getManager());
        }

        return new ArrayList<>(participants);
    }
}
