package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.PageOfUserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserUpdateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface IUserService {
    User save(UserCreateDTO item);
    PageOfUserDTO get(PageRequest pageRequest);
    User get(UUID uuid);
    User update(UserUpdateDTO item);
    void registration(UserRegistrationDTO item);
    boolean activate(String email, String code);

}
