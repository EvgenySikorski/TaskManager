package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserDTOToUserConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserToUserDTOConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IUserDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final UserDTOToUserConvertor converter;
    private final UserToUserDTOConvertor converterUserToDTO;


    public UserService(IUserDao userDao, UserDTOToUserConvertor converter, UserToUserDTOConvertor converterUserToDTO) {
        this.userDao = userDao;
        this.converter = converter;
        this.converterUserToDTO = converterUserToDTO;
    }

    @Override
    public User save(UserCreateDTO item) {
        UserDTO userDTO = new UserDTO(item.getFio(), item.getMail(),item.getRole(), item.getStatus());
        UUID activationCode = UUID.randomUUID(); //TODO разобраться с кодом активации
        User userCreat = this.converter.convert(userDTO);
        userCreat.setPassword(item.getPassword());
        userCreat.setActivationCode(activationCode);
        userCreat.setUuid(UUID.randomUUID());

        return userDao.save(userCreat);
    }

    @Override
    public void registration(UserRegistrationDTO item) {
        UserDTO userDTO = new UserDTO(item.getFio(), item.getMail(), EUserRole.USER.name(), EUserStatus.WAITING_ACTIVATION.name());
        UUID activationCode = UUID.randomUUID(); //TODO разобраться с кодом активации
        User userCreat = this.converter.convert(userDTO);
        userCreat.setPassword(item.getPassword());
        userCreat.setActivationCode(activationCode);
        userCreat.setUuid(UUID.randomUUID());

        userDao.save(userCreat);
    }

    @Override
    public PageOfUserDTO get(PageRequest pageRequest) {
        Page<User> userPage = userDao.findAll(pageRequest);
        return new PageOfUserDTO(userPage.getNumber(),userPage.getSize(),
                userPage.getTotalPages(), userPage.getTotalElements(), userPage.isFirst(),
                userPage.getNumberOfElements(), userPage.isLast(), userPage.get().map(this.converterUserToDTO::convert).toList());
    }

    @Override
    public User get(UUID uuid) {
        return userDao.findById(uuid).orElseThrow(()->new IllegalArgumentException("user с указанным id не найден"));
    }

    @Override
    public User update(UserUpdateDTO item) {
        UserDTO userDTO = new UserDTO(item.getUuid(), item.getMail(), item.getFio(),
                item.getRole(), item.getStatus(),item.getDt_update());
        User user = this.converter.convert(userDTO);
        user.setPassword(item.getPassword());
        User userFromDB = get(item.getUuid());
        if (user.getDt_update().isEqual(userFromDB.getDt_update())){
            return userDao.save(user);
        } else{
            throw new RuntimeException("дата обновления не совпадает");
        }
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

    public User convertorUserDTOtoUser(UserDTO userDTO){
        User user = new User();
        user.setFio(userDTO.getFio());
        user.setMail(userDTO.getMail());

        EUserRole role = null;
        for (EUserRole value : EUserRole.values()) {
            if(userDTO.getRole().equals(value.name())){
                role = value;
            } else{
                throw new RuntimeException("Указанная роль не доступна");
            }
        }
        user.setRole(role);

        EUserStatus status = null;
        for (EUserStatus value : EUserStatus.values()) {
            if(userDTO.getStatus().equals(value.name())){
                status = value;
            } else{
                throw new RuntimeException("Указанный статус не доступен");
            }
        }
        user.setStatus(status);

        return user;
    }

}
