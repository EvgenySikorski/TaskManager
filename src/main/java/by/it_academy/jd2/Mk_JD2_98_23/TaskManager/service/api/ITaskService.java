package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.TaskCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.TaskFilterDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.TaskStatusUpdateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.TaskUpdateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ITaskService {

    Task save(TaskCreateDTO item);

    Page<Task> get(PageRequest pageRequest, TaskFilterDTO filter);

    Task get(UUID uuid);

    Task update(TaskCreateDTO item, UUID uuid, LocalDateTime dtUpdate);

    Task updateStatus (TaskStatusUpdateDTO item);
}
