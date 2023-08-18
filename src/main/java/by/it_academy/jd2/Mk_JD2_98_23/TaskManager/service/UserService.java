package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserDTOToUserConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserToUserDTOConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IUserDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.MailAlreadyExistsException;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.UserNotFoundException;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.VersionException;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuditService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.UUID;
@Service
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final UserDTOToUserConvertor converterDTOToUser;
    private final UserToUserDTOConvertor convertorUserToDTO;
    private final IAuditService auditService;
    private final PasswordEncoder encoder;
    private final UserHolder userHolder;

    public UserService(IUserDao userDao, UserDTOToUserConvertor converterDTOToUser,
                       UserToUserDTOConvertor convertorUserToDTO,
                       IAuditService auditService, PasswordEncoder encoder, UserHolder userHolder) {
        this.userDao = userDao;
        this.converterDTOToUser = converterDTOToUser;
        this.convertorUserToDTO = convertorUserToDTO;
        this.auditService = auditService;
        this.encoder = encoder;
        this.userHolder = userHolder;
    }

    @Override
    public User save(UserCreateDTO item) {
        if (userDao.findByMail(item.getMail()) != null){
            throw new MailAlreadyExistsException(item.getMail());
        }

        UserDTO userDTO = new UserDTO(item.getFio(), item.getMail(),item.getRole(), item.getStatus());
        User userCreat = this.converterDTOToUser.convert(userDTO);
        userCreat.setPassword(encoder.encode(item.getPassword()));
        userCreat.setUuid(UUID.randomUUID());

        if (item.getStatus().equals("WAITING_ACTIVATION")){
            UUID activationCode = UUID.randomUUID();
            userCreat.setActivationCode(activationCode);
        }

        User saveUser = this.userDao.save(userCreat);
        saveActionToAudit("Сохранен новый пользователь", saveUser.getUuid().toString(), EEssenceType.USER);

        return saveUser;
    }

    @Override
    public Page<User> get(PageRequest pageRequest) {
        return this.userDao.findAll(pageRequest);
    }

    @Override
    public User get(UUID uuid) {
        return userDao.findById(uuid).orElseThrow(()->new UserNotFoundException(uuid));
    }

    @Override
    public User update(UserUpdateDTO item) {
        UserDTO userDTO = new UserDTO(item.getUuid(), item.getMail(), item.getFio(),
                item.getRole(), item.getStatus(),item.getDateUpdate().toInstant(ZoneOffset.UTC).toEpochMilli());
        User user = this.converterDTOToUser.convert(userDTO);
        user.setPassword(encoder.encode(item.getPassword()));
        User userFromDB = get(item.getUuid());
        if (!user.getDt_update().toString().equals(userFromDB.getDt_update().toString())){
            throw new VersionException();
        }
        userDao.delete(userFromDB);

        User updateUserFromDB = userDao.save(user);
        saveActionToAudit("Пользователь обновлен",updateUserFromDB.getUuid().toString(), EEssenceType.USER);

        return updateUserFromDB;
    }

    public User updateStatus(User user) {
        User updateUserFromDB = userDao.save(user);
        UserDTO userDTOForAudit = this.convertorUserToDTO.convert(updateUserFromDB);
        saveActionToAudit("Пользователь активирован",updateUserFromDB.getUuid().toString(), EEssenceType.USER);
        return updateUserFromDB;
    }



    private void saveActionToAudit(String text, String id, EEssenceType essenceType){
        String userEmail = userHolder.getUser().getUsername();
        User user = getCardByMail(userEmail);
        AuditCreatDTO auditCreatDTO = new AuditCreatDTO(
            user.getUuid(),
            user.getMail(),
            user.getFio(),
            user.getRole(),
            text,
            essenceType,
            id
        );
        this.auditService.save(auditCreatDTO);
    }

    @Override
    public User getCardByMail(String email) {
        return userDao.findByMail(email);
    }
}
