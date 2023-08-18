package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.UserRefDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EProjectStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProjectCreateDTOToProjectConverter implements Converter<ProjectCreatDTO, Project> {
    @Override
    public Project convert(ProjectCreatDTO source) {
        Project project = new Project();
        project.setUuid(UUID.randomUUID());
        project.setName(source.getName());
        project.setDescription(source.getDescription());
        project.setManager(source.getManager().getUuid());

        Set<UUID> setUserRef = new HashSet<>();
        for (UserRefDTO staff : source.getStaff()) {
            setUserRef.add(staff.getUuid());
            project.setStaff(setUserRef);
        }

        project.setStatus(source.getStatus());
        return project;
    }
}
