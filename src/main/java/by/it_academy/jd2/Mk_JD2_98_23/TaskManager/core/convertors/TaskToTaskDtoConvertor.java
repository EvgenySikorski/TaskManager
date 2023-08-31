package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectRefDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.TaskDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.UserRefDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Task;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;

public class TaskToTaskDtoConvertor implements Converter<Task, TaskDTO> {
    @Override
    public TaskDTO convert(Task source) {
        return new TaskDTO(
                source.getUuid(),
                source.getDtCreat().toInstant(ZoneOffset.UTC).toEpochMilli(),
                source.getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli(),
                new ProjectRefDTO(source.getProject().getUuid()),
                source.getTitle(),
                source.getDescription(),
                source.getStatus(),
                new UserRefDTO(source.getImplementer())
                );
    }
}
