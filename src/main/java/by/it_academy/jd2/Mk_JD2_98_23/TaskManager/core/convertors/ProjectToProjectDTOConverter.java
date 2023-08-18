package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.UserRefDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EProjectStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProjectToProjectDTOConverter implements Converter<Project, ProjectDTO> {
    @Override
    public ProjectDTO convert(Project source) {
        return new ProjectDTO(
                source.getUuid(),
                source.getDtCreat().toInstant(ZoneOffset.UTC).toEpochMilli(),
                source.getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli(),
                source.getName(),
                source.getDescription(),
                new UserRefDTO(source.getManager()),
                source.getStaff().stream().map(UserRefDTO::new).collect(Collectors.toSet()),
                source.getStatus()
                );
    }
}
