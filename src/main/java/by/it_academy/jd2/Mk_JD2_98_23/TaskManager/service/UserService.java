package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserDTOToUserConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserToUserDTOConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.api.IUserDao;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final UserDTOToUserConvertor converterDTOToUser;
    private final UserToUserDTOConvertor converterUserToDTO;



    public UserService(IUserDao userDao, UserDTOToUserConvertor converter, UserToUserDTOConvertor converterUserToDTO) {
        this.userDao = userDao;
        this.converterDTOToUser = converter;
        this.converterUserToDTO = converterUserToDTO;
    }

    @Override
    public User save(UserCreateDTO item) {
        userDao.findByMail(item.getMail()).ifPresent(u -> {throw new RuntimeException("аккаунт с таким емайлом уже создан");});
        UserDTO userDTO = new UserDTO(item.getFio(), item.getMail(),item.getRole(), item.getStatus());
        User userCreat = this.converterDTOToUser.convert(userDTO);
        userCreat.setPassword(item.getPassword());
        userCreat.setUuid(UUID.randomUUID());

        return userDao.save(userCreat);
    }

    @Override
    public Page<User> get(PageRequest pageRequest) {
        Page<User> userPage = userDao.findAll(pageRequest);
        return userPage;
    }

    @Override
    public User get(UUID uuid) {
        return userDao.findById(uuid).orElseThrow(()->new IllegalArgumentException("user с указанным id не найден"));
    }

    @Override
    public User update(UserUpdateDTO item) {
        UserDTO userDTO = new UserDTO(item.getUuid(), item.getMail(), item.getFio(),
                item.getRole(), item.getStatus(),item.getDt_update());
        User user = this.converterDTOToUser.convert(userDTO);
        user.setPassword(item.getPassword());
        User userFromDB = get(item.getUuid());
        if (user.getDt_update().isEqual(userFromDB.getDt_update())){
            return userDao.save(user);
        } else{
            throw new RuntimeException("дата обновления не совпадает");
        }
    }
}
