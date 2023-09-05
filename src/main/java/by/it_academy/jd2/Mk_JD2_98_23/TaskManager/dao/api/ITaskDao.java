package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.ETaskStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Project;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ITaskDao extends JpaRepository<Task, UUID>, ListPagingAndSortingRepository<Task, UUID> {

    Page<Task> findByImplementerIn(
            List<UUID> implementers,
            Pageable pageable
    );

    Page<Task> findByProjectUuidIn(
            List<UUID> projects,
            Pageable pageable
    );

    Page<Task> findByStatusIn(
            List<ETaskStatus> statuses,
            Pageable pageable
    );

    Page<Task> findByProjectUuidInAndImplementerIn(
            List<UUID> projects,
            List<UUID> implementers,
            Pageable pageable
    );

    Page<Task> findByImplementerInAndStatusIn(
            List<UUID> implementers,
            List<ETaskStatus> statuses,
            Pageable pageable
    );

    Page<Task> findByProjectUuidInAndStatusIn(
            List<UUID> projects,
            List<ETaskStatus> statuses,
            Pageable pageable
    );

    Page<Task> findByProjectUuidInAndStatusInAndImplementerIn(
            List<UUID> projects,
            List<ETaskStatus> statuses,
            List<UUID> implementers,
            Pageable pageable
    );

    Optional<Task> findByUuidAndProjectUuidIn(
            UUID uuid, List<UUID> projects
    );



    Page<Task> findAllByProjectIn(Pageable pageable, List<Project> projects);
    Page<Task> findAllByProjectInAndImplementerIn(Pageable pageable, List<Project> projects, List<UUID> implementers);

    Page<Task> findAllByProjectInAndStatusIn(Pageable pageable, List<Project> projects, List<ETaskStatus> statuses);


}
