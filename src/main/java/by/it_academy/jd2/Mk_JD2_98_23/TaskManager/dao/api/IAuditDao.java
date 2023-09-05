package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Audit;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAuditDao extends JpaRepository<Audit, UUID>, PagingAndSortingRepository<Audit, UUID> {

    Page<Audit> findAll(Pageable pageable);

    @Override
    Optional<Audit> findById(UUID uuid);

    @Override
    <S extends Audit> S save(S entity);
}
