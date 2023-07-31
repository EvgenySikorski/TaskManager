package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserDao extends JpaRepository<User, UUID> {

    @Override
    <S extends User> S save(S entity);

    @Override
    Page<User> findAll(Pageable pageable);

    @Override
    Optional<User> findById(UUID uuid);

    Optional<User> findByMail(String email);

    User findByMailAndActivationCode(String email, UUID acticationCode);


}