package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EProjectStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface IProjectDao extends JpaRepository<Project, UUID> {

    Page<Project> findAllByStatus(PageRequest pageRequest, EProjectStatus status);

    List<Project> findByManagerOrStaff(
            UUID manager, UUID staff);

    Page<Project> findByManagerOrStaff(
            UUID manager, UUID staff, Pageable pageable);

    List<Project> findByUuidInAndManagerOrUuidInAndStaff(
            List<UUID> project1, UUID manager,
            List<UUID> project2, UUID staff);

    Page<Project> findByStatusAndManagerOrStatusAndStaff(
            EProjectStatus status, UUID manager,
            EProjectStatus statusN, UUID staff,
            Pageable pageable
    );
}
