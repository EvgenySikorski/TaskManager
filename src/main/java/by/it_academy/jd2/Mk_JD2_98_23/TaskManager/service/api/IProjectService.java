package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectUpdateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IProjectService {

    Project save(ProjectCreateDTO item);

    Page<Project> get(PageRequest pageRequest, boolean archived);

    Project get(UUID uuid);

    Project update(ProjectUpdateDTO item);

    List<Project> getByUser(UUID userUuid);

    List<Project> get(List<UUID> projectUuids, UUID user);
    List<Project> findAllByIdIn(Collection<UUID> uuid);




}
