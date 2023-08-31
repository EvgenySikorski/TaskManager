package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.controller;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.PageDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectUpdateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IProjectService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

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
    public ResponseEntity<ProjectDTO> save(@RequestBody ProjectCreateDTO projectCreateDTO){
        Project createProject = this.projectService.save(projectCreateDTO);
        ProjectDTO projectDTO = this.conversionService.convert(createProject, ProjectDTO.class);
        return new ResponseEntity<>(projectDTO, HttpStatus.CREATED);
    }
    @GetMapping(consumes = "application/json", produces = "application/json" )
    public ResponseEntity<PageDTO<ProjectDTO>> page(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "20") int size,
                                                 @RequestParam(defaultValue = "false") boolean archived) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Project> project = this.projectService.get(pageRequest, archived);

        PageDTO<ProjectDTO> pageOfProjectDTO = new PageDTO<>(project.getNumber(),project.getSize(),
                project.getTotalPages(), project.getTotalElements(), project.isFirst(),
                project.getNumberOfElements(), project.isLast(),
                project.get().map(u -> conversionService.convert(u, ProjectDTO.class)).toList());

        return new ResponseEntity<>(pageOfProjectDTO, HttpStatus.OK);
    }

    @GetMapping(value = "{uuid}", consumes = "application/json", produces = "application/json" )
    public ResponseEntity<ProjectDTO> readCard(@PathVariable UUID uuid) {
        Project project = this.projectService.get(uuid);
        ProjectDTO projectDTO = this.conversionService.convert(project, ProjectDTO.class);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ProjectDTO> update(@PathVariable UUID uuid,
                                          @PathVariable ("dt_update") LocalDateTime updateDate,
                                          @RequestBody ProjectUpdateDTO projectUpdateDTO){
        projectUpdateDTO.setDateUpdate(updateDate);
        projectUpdateDTO.setUuid(uuid);

        Project project = this.projectService.update(projectUpdateDTO);
        ProjectDTO projectDTO = this.conversionService.convert(project, ProjectDTO.class);

        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }
}
