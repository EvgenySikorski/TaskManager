package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.ProjectUpdateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface IProjectService {

    Project save(ProjectCreatDTO item);

    Page<Project> get(PageRequest pageRequest, boolean archived);

    Project get(UUID uuid);

    Project update(ProjectUpdateDTO item);


}
