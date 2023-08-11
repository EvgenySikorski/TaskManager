package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserDTOToUserConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.LoginDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserCreateDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IUserDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.controller.utils.JwtTokenHandler;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.MailAlreadyExistsException;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.PasswordWrongException;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuthenticationService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.INotificationService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class AuthenticationService implements IAuthenticationService {

    private final IUserService userService;
    private final UserDTOToUserConvertor converterDTOToUser;
    private final PasswordEncoder encoder;
    private final JwtTokenHandler jwtHandler;
    private final UserDetailsService detailsService;
    private final INotificationService notificationService;


    public AuthenticationService(IUserService userService, UserDTOToUserConvertor converterDTOToUser,
                                 PasswordEncoder encoder, JwtTokenHandler jwtHandler,
                                 UserDetailsService detailsService, INotificationService notificationService) {
        this.userService = userService;
        this.converterDTOToUser = converterDTOToUser;
        this.encoder = encoder;
        this.jwtHandler = jwtHandler;
        this.detailsService = detailsService;
        this.notificationService = notificationService;
    }

    @Override
    public void registration(UserRegistrationDTO item) {
        if (userService.getCardByMail(item.getMail()) != null){
            throw new MailAlreadyExistsException(item.getMail());
        };

        UserCreateDTO userCreateDTO = new UserCreateDTO(
                item.getMail(),
                item.getFio(),
                EUserRole.USER.name(),
                EUserStatus.WAITING_ACTIVATION.name(),
                encoder.encode(item.getPassword())
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

    @Override
    public String login(LoginDTO dto) {
        UserDetails userDetails = detailsService.loadUserByUsername(dto.getMail());
        if (encoder.matches(dto.getPassword(),userDetails.getPassword())){
            throw new PasswordWrongException();
        }
        if (!dto.getPassword().equals(userDetails.getPassword())){
            throw new PasswordWrongException();
        }

        if(!userDetails.isAccountNonLocked()){
            throw new RuntimeException("акаунт не активен");
        }
        return jwtHandler.generateAccessToken(userDetails);
    }
}
