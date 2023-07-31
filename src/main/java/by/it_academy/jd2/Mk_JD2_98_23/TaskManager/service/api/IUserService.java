package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserUpdateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface IUserService {
    User save(UserCreateDTO item);
    Page<User> get(PageRequest pageRequest);
    User get(UUID uuid);
    User update(UserUpdateDTO item);


}
