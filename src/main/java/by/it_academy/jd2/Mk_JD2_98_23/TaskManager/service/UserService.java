package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IUserDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.MailAlreadyExistsException;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.UserNotFoundException;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.VersionException;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuditService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.UUID;
@Service
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final ConversionService conversionService;
    private final IAuditService auditService;
    private final PasswordEncoder encoder;
    private final UserHolder userHolder;

    public UserService(IUserDao userDao, ConversionService conversionService, IAuditService auditService,
                       PasswordEncoder encoder, UserHolder userHolder) {
        this.userDao = userDao;
        this.conversionService = conversionService;
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
        User userCreat = this.conversionService.convert(userDTO, User.class);
        userCreat.setPassword(encoder.encode(item.getPassword()));
        userCreat.setUuid(UUID.randomUUID());

        if (item.getStatus().equals("WAITING_ACTIVATION")){
            UUID activationCode = UUID.randomUUID();
            userCreat.setActivationCode(activationCode);
        }

        User saveUser = this.userDao.save(userCreat);
        saveActionToAudit("Сохранен новый пользователь", saveUser, EEssenceType.USER);

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
        User user = this.conversionService.convert(userDTO, User.class);
        user.setPassword(encoder.encode(item.getPassword()));
        User userFromDB = this.get(item.getUuid());
        if (!item.getDateUpdate().isEqual(userFromDB.getDt_update())){
            throw new VersionException("Версии не совпадают");
        }

        userDao.delete(userFromDB);

        User updateUserFromDB = userDao.save(user);
        saveActionToAudit("Пользователь обновлен",updateUserFromDB, EEssenceType.USER);

        return updateUserFromDB;
    }

    public User updateStatus(User user) {
        User updateUserFromDB = userDao.save(user);
        UserDTO userDTOForAudit = this.conversionService.convert(updateUserFromDB, UserDTO.class);
        saveActionToAudit("Пользователь активирован",updateUserFromDB, EEssenceType.USER);
        return updateUserFromDB;
    }

    private void saveActionToAudit(String text, User user, EEssenceType essenceType){
            AuditCreatDTO auditCreatDTO = new AuditCreatDTO(
            user.getUuid(),
            user.getMail(),
            user.getFio(),
            user.getRole(),
            text,
            essenceType,
            user.getUuid().toString()
        );
        this.auditService.save(auditCreatDTO);
    }

    @Override
    public User getCardByMail(String email) {
        return userDao.findByMail(email);
    }


}
