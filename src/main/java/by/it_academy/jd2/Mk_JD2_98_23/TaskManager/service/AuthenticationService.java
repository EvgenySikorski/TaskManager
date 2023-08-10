package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserDTOToUserConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.LoginDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IUserDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.controller.utils.JwtTokenHandler;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.PasswordWrongException;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuthenticationService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class AuthenticationService implements IAuthenticationService {

    private final IUserDao userDao;
    private final UserDTOToUserConvertor converterDTOToUser;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder encoder;
    private final JwtTokenHandler jwtHandler;
    private final UserDetailsService detailsService;


    public AuthenticationService(IUserDao userDao, UserDTOToUserConvertor converterDTOToUser,
                                 MailSenderService mailSenderService, PasswordEncoder encoder,
                                 JwtTokenHandler jwtHandler, UserDetailsService detailsService) {
        this.userDao = userDao;
        this.converterDTOToUser = converterDTOToUser;
        this.mailSenderService = mailSenderService;
        this.encoder = encoder;
        this.jwtHandler = jwtHandler;
        this.detailsService = detailsService;
    }

    @Override
    public void registration(UserRegistrationDTO item) {
        userDao.findByMail(item.getMail()).ifPresent(u -> {throw new IllegalArgumentException("аккаунт с таким email уже создан");});

        UserDTO userDTO = new UserDTO(item.getFio(), item.getMail(), EUserRole.USER.name(), EUserStatus.WAITING_ACTIVATION.name());
        UUID activationCode = UUID.randomUUID();
        User userCreat = this.converterDTOToUser.convert(userDTO);
        userCreat.setPassword(item.getPassword());
        userCreat.setActivationCode(activationCode);
        userCreat.setUuid(UUID.randomUUID());

        userDao.save(userCreat);

        String message = String.format(
                "Hello! " +
                        "Welcome to TaskManager. Please, for activation accaunt " +
                        "follow this link: http://localhost:80/users/verification?code=" + userCreat.getActivationCode() + "&mail=" + userCreat.getMail()
                        );
        mailSenderService.send(item.getMail(), "Activation code", message);
    }

    @Override
    public boolean activate(String email, String code) {
        User user = userDao.findByMailAndActivationCode(email, UUID.fromString(code));
        if (user == null){
            return false;
        }
        user.setActivationCode(null);
        user.setStatus(EUserStatus.ACTIVATED);

        userDao.save(user);
        return true;
    }

    @Override
    public String login(LoginDTO dto) {
        UserDetails userDetails = detailsService.loadUserByUsername(dto.getMail());
//        if (encoder.matches(dto.getPassword(),userDetails.getPassword())){
//            throw new PasswordWrongException();
//        }
        if (!dto.getPassword().equals(userDetails.getPassword())){
            throw new PasswordWrongException();
        }

        if(!userDetails.isAccountNonLocked()){
            throw new RuntimeException("акаунт не активен");
        }
        return jwtHandler.generateAccessToken(userDetails);
    }
}
