package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EProjectStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IProjectDao extends JpaRepository<Project, UUID> {

    Page<Project> findAllByStatus(PageRequest pageRequest, EProjectStatus status);

    List<Project> findAllByStaffContains(UUID uuid);

}
