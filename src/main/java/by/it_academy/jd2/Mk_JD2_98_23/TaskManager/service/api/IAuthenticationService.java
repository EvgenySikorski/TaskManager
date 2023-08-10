package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.LoginDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserRegistrationDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationService {

    void registration(UserRegistrationDTO item);
    boolean activate(String email, String code);
    String login(LoginDTO dto);

}
