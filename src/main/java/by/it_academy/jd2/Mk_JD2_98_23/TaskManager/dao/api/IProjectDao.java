package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProjectDao extends JpaRepository<Project, UUID> {

}
