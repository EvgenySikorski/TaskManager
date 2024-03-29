package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserDTOToUserConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EErrorType;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.errors.ErrorResponse;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IUserDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.controller.utils.JwtTokenHandler;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuthenticationService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.INotificationService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AuthenticationService implements IAuthenticationService {

    private static final String FIELD_NAME_MAIL = "mail";
    private static final String FIELD_NAME_FIO = "fio";
    private static final String FIELD_NAME_PASSWORD = "password";

    private final IUserService userService;
    private final PasswordEncoder encoder;
    private final JwtTokenHandler jwtHandler;
    private final UserHolder userHolder;
    private final INotificationService notificationService;

    public AuthenticationService(IUserService userService, PasswordEncoder encoder, JwtTokenHandler jwtHandler,
                                 UserHolder userHolder, INotificationService notificationService) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtHandler = jwtHandler;
        this.userHolder = userHolder;
        this.notificationService = notificationService;
    }

    @Override
    public void registration(UserRegistrationDTO item) {

        validate(item);

        if (userService.getCardByMail(item.getMail()) != null){
            throw new MailAlreadyExistsException(item.getMail());
        };

        UserCreateDTO userCreateDTO = new UserCreateDTO(
                item.getMail(),
                item.getFio(),
                EUserRole.USER.name(),
                EUserStatus.WAITING_ACTIVATION.name(),
                item.getPassword()
        );

        notificationService.send(userService.save(userCreateDTO));

    }

    @Override
    public boolean activate(String email, UUID code) {
        User user = userService.getCardByMail(email);
        if (user == null || (!code.equals(user.getActivationCode()))){
            return false;
        }
        user.setActivationCode(null);
        user.setStatus(EUserStatus.ACTIVATED);

        userService.updateStatus(user);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public String login(LoginDTO dto) {

        validate(dto);

        User user = this.userService.getCardByMail(dto.getMail());
        if (user == null){
            throw new UserNotFoundException("Пользователь с указазным e-mail не найден");
        }


        EUserStatus status = user.getStatus();

        if (EUserStatus.DEACTIVATED.equals(status)){
            throw new DeactivatedUserException();
        } else if (EUserStatus.WAITING_ACTIVATION.equals(status)){
            throw new NoActivatedUserException();
        }

        if (user.getRole().equals(EUserRole.ADMIN)){
            if (!dto.getPassword().equals(user.getPassword())){
                throw new PasswordWrongException();
            }
            return jwtHandler.generateAccessToken(user.getUuid(), user.getMail(), user.getFio(), user.getRole());
        }

        if (!this.encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new PasswordWrongException();
        }

        return jwtHandler.generateAccessToken(user.getUuid(), user.getMail(), user.getFio(), user.getRole());
    }

    @Transactional(readOnly = true)
    @Override
    public User getMe() {
        UserDetailsImpl user = this.userHolder.getUser();
        return this.userService.get(user.getUuid());
    }

    private void validate(UserRegistrationDTO item){
        Map<String, String> errors = new HashMap<>();

        String mail = item.getMail();
        if (mail == null || "".equals(mail)){
            errors.put(FIELD_NAME_MAIL, "e-mail не указан");
        }

        String fio = item.getFio();
        if (fio == null || "".equals(fio)){
            errors.put(FIELD_NAME_FIO, "ФИО не указано");
        }

        String password = item.getPassword();
        if (password == null || "".equals(password)){
            errors.put(FIELD_NAME_PASSWORD, "Пароль не указан");
        }

        if (!errors.isEmpty()){
            throw new NotValidBodyException(errors);
        }
    }

        private void validate(LoginDTO item){
        Map<String, String> errors = new HashMap<>();

        String mail = item.getMail();
        if (mail == null || "".equals(mail)){
            errors.put(FIELD_NAME_MAIL, "e-mail не указан");
        }

        String password = item.getPassword();
        if (password == null || "".equals(password)){
            errors.put(FIELD_NAME_PASSWORD, "Пароль не указан");
        }

        if (!errors.isEmpty()){
            throw new NotValidBodyException(errors);
        }
    }
}
