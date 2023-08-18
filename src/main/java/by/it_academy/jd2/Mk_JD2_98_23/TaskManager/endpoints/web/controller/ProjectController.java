package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.controller;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IProjectService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final IProjectService projectService;
    private final ConversionService conversionService;


    public ProjectController(IProjectService projectService, ConversionService conversionService) {
        this.projectService = projectService;
        this.conversionService = conversionService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProjectDTO> save(@RequestBody ProjectCreatDTO projectCreatDTO){
        Project createProject = this.projectService.save(projectCreatDTO);
        ProjectDTO projectDTO = this.conversionService.convert(createProject, ProjectDTO.class);
        return new ResponseEntity<>(projectDTO, HttpStatus.CREATED);
    }

}
