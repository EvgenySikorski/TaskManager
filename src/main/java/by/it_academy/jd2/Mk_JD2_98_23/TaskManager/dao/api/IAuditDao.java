package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IAuditDao extends JpaRepository<Audit, UUID> {

    Page<Audit> findAll(Pageable pageable);

    @Override
    Optional<Audit> findById(UUID uuid);

    @Override
    <S extends Audit> S save(S entity);
}
