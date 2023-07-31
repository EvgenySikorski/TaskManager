package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserRegistrationDTO;

public interface IAuthenticationService {

    void registration(UserRegistrationDTO item);
    boolean activate(String email, String code);
}
