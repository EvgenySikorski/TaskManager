package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit.AuditCreatDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IUserDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuditService;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class UserService implements IUserService {

    private static final String FIELD_NAME_MAIL = "mail";
    private static final String FIELD_NAME_FIO = "fio";
    private static final String FIELD_NAME_ROLE = "role";
    private static final String FIELD_NAME_STATUS = "status";
    private static final String FIELD_NAME_PASSWORD = "password";
    private static final String FIELD_NAME_UUID = "uuid";
    private static final String FIELD_NAME_DATEUPDATE = "dateUpdate";

    private static final String INCORRECT_DATA = "Неверные данные. Попробуйте еще раз";



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

    @Transactional
    @Override
    public User save(UserCreateDTO item) {

        validate(item);

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
        if (pageRequest.getPageNumber() < 0 || pageRequest.getPageSize() < 1){
            throw new IncorrectDataExeption(INCORRECT_DATA);
        }

        return this.userDao.findAll(pageRequest);
    }

    @Override
    public User get(UUID uuid) {
        return userDao.findById(uuid).orElseThrow(()->new UserNotFoundException("Пользователь с UUID " + uuid + " не найден"));
    }

    @Override
    public User update(UserUpdateDTO item) {
        validate(item);

        User userFromDB = this.get(item.getUuid());
        if (!item.getDateUpdate().isEqual(userFromDB.getDt_update())){
            throw new VersionException("Версии не совпадают");
        }

        UserDTO userDTO = new UserDTO(item.getUuid(), item.getMail(), item.getFio(),
                item.getRole(), item.getStatus(),item.getDateUpdate().toInstant(ZoneOffset.UTC).toEpochMilli(),
                userFromDB.getDt_create().toInstant(ZoneOffset.UTC).toEpochMilli());
        User user = this.conversionService.convert(userDTO, User.class);
        user.setPassword(encoder.encode(item.getPassword()));

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

    @Override
    public List<User> findAllById(Set<UUID> uuids) {
        return userDao.findAllById(uuids);
    }

    private void validate(UserCreateDTO item){
        Map<String, String> errors = new HashMap<>();

        String mail = item.getMail();
        if (mail == null || "".equals(mail)){
            errors.put(FIELD_NAME_MAIL, "e-mail не указан");
        }

        String fio = item.getFio();
        if (fio == null || "".equals(fio)){
            errors.put(FIELD_NAME_FIO, "ФИО не указано");
        }

        String role = item.getRole();
        if (role == null || "".equals(role)){
            errors.put(FIELD_NAME_ROLE, "Роль не указана");
        } else {
            EUserRole[] arrUserRole = EUserRole.values();
            String[] arrRole = new String[arrUserRole.length];
            for (int i = 0; i < arrUserRole.length; i++) {
                arrRole[i] = arrUserRole[i].name();
            }

            if (!Arrays.asList(arrRole).contains(role)){
                errors.put(FIELD_NAME_ROLE, "Роль указана не верно");
            }
        }

        String status = item.getStatus();
        if (status == null || "".equals(status)){
            errors.put(FIELD_NAME_STATUS, "Статус не указан");
        } else{
            EUserStatus[] arrUserStatus = EUserStatus.values();
            String[] arrStatus = new String[arrUserStatus.length];
            for (int i = 0; i < arrUserStatus.length; i++) {
                arrStatus[i] = arrUserStatus[i].name();
            }
            if (!Arrays.asList(arrStatus).contains(status)){
                errors.put(FIELD_NAME_STATUS, "Статус указан не верно");
            }
        }

        String password = item.getPassword();
        if (password == null || "".equals(password)){
            errors.put(FIELD_NAME_PASSWORD, "Пароль не указан");
        }

        if (!errors.isEmpty()){
            throw new NotValidBodyException(errors);
        }
    }

    private void validate(UserUpdateDTO item){
        Map<String, String> errors = new HashMap<>();

        String mail = item.getMail();
        if (mail == null || "".equals(mail)){
            errors.put(FIELD_NAME_MAIL, "e-mail не указан");
        }

        String fio = item.getFio();
        if (fio == null || "".equals(fio)){
            errors.put(FIELD_NAME_FIO, "ФИО не указано");
        }

        String role = item.getRole();
        if (role == null || "".equals(role)){
            errors.put(FIELD_NAME_ROLE, "Роль не указана");
        } else {
            EUserRole[] arrUserRole = EUserRole.values();
            String[] arrRole = new String[arrUserRole.length];
            for (int i = 0; i < arrUserRole.length; i++) {
                arrRole[i] = arrUserRole[i].name();
            }

            if (!Arrays.asList(arrRole).contains(role)){
                errors.put(FIELD_NAME_ROLE, "Роль указана не верно");
            }
        }

        String status = item.getStatus();
        if (status == null || "".equals(status)){
            errors.put(FIELD_NAME_STATUS, "Статус не указан");
        } else{
            EUserStatus[] arrUserStatus = EUserStatus.values();
            String[] arrStatus = new String[arrUserStatus.length];
            for (int i = 0; i < arrUserStatus.length; i++) {
                arrStatus[i] = arrUserStatus[i].name();
            }
            if (!Arrays.asList(arrStatus).contains(status)){
                errors.put(FIELD_NAME_STATUS, "Статус указан не верно");
            }
        }

        String password = item.getPassword();
        if (password == null || "".equals(password)){
            errors.put(FIELD_NAME_PASSWORD, "Пароль не указан");
        }

        UUID uuid = item.getUuid();
        if (uuid.toString() == null || "".equals(uuid.toString())){
            errors.put(FIELD_NAME_UUID, "UUID пользователя не указан");
        }

        LocalDateTime dateUpdate = item.getDateUpdate();
        if (dateUpdate == null){
            errors.put(FIELD_NAME_DATEUPDATE, "Дата обновления пользователя не указана");
        }

        if (!errors.isEmpty()){
            throw new NotValidBodyException(errors);
        }
    }
}
