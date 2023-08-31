package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.controller;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.PageDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.ETaskStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Task;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.ITaskService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final ITaskService taskService;
    private final ConversionService conversionService;

    public TaskController(ITaskService taskService, ConversionService conversionService) {
        this.taskService = taskService;
        this.conversionService = conversionService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TaskDTO> save(@RequestBody TaskCreateDTO taskCreateDTO){
        Task createTask = this.taskService.save(taskCreateDTO);
        TaskDTO taskDTO = this.conversionService.convert(createTask, TaskDTO.class);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @GetMapping(consumes = "application/json", produces = "application/json" )
    public ResponseEntity<PageDTO<TaskDTO>> page(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) List<UUID> project,
            @RequestParam(required = false) List<UUID> implementer,
            @RequestParam(required = false) List<ETaskStatus> status
    )    {
        PageRequest pageRequest = PageRequest.of(page, size);
        TaskFilterDTO filterDTO = new TaskFilterDTO(project, implementer, status);

        Page<Task> task = this.taskService.get(pageRequest, filterDTO);

        PageDTO<TaskDTO> pageOfTaskDTO = new PageDTO<>(task.getNumber(),task.getSize(),
                task.getTotalPages(), task.getTotalElements(), task.isFirst(),
                task.getNumberOfElements(), task.isLast(),
                task.get().map(u -> conversionService.convert(u, TaskDTO.class)).toList());

        return new ResponseEntity<>(pageOfTaskDTO, HttpStatus.OK);
    }

    @GetMapping(value = "{uuid}", consumes = "application/json", produces = "application/json" )
    public ResponseEntity<TaskDTO> readCard(@PathVariable UUID uuid) {
        Task task = this.taskService.get(uuid);
        TaskDTO taskDTO = this.conversionService.convert(task, TaskDTO.class);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TaskDTO> update(@PathVariable UUID uuid,
                                             @PathVariable ("dt_update") LocalDateTime updateDate,
                                             @RequestBody TaskCreateDTO taskCreateDTO){

        Task task = this.taskService.update(taskCreateDTO, uuid, updateDate);
        TaskDTO taskDTO = this.conversionService.convert(task, TaskDTO.class);

        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PatchMapping(value = "/{uuid}/dt_update/{dt_update}/status/{status}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TaskDTO> updateStatus(@PathVariable UUID uuid,
                                          @PathVariable ("dt_update") LocalDateTime updateDate,
                                          @PathVariable ETaskStatus status){

        TaskStatusUpdateDTO taskStatusUpdateDTO = new TaskStatusUpdateDTO(uuid, updateDate, status);
        Task task = this.taskService.updateStatus(taskStatusUpdateDTO);
        TaskDTO taskDTO = this.conversionService.convert(task, TaskDTO.class);

        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }
}
