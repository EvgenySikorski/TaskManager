package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class UserDTOToUserConvertor implements Converter<UserDTO, User> {

    @Override
    public User convert(UserDTO source) {
        User user = new User(source.getUuid(), source.getMail(), source.getFio());
        if(source.getRole() != null ){
            user.setRole(EUserRole.valueOf(source.getRole()));
        }
        if(source.getStatus() != null){
            user.setStatus(EUserStatus.valueOf(source.getStatus()));
        }
        if (source.getDtCreate() != null){
            user.setDt_create(LocalDateTime.ofInstant(Instant.ofEpochMilli(source.getDtCreate()), ZoneOffset.UTC));
        }
        if(source.getDtUpdate() != null){
            user.setDt_update(LocalDateTime.ofInstant(Instant.ofEpochMilli(source.getDtUpdate()), ZoneOffset.UTC));
        }
        return user;    }
}
