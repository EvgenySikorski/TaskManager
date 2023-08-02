package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.ConversionException;
import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class UserDTOToUserConvertor implements Converter<UserDTO, User> {

    @Override
    public User convert(UserDTO source) {
        User user = new User(source.getUuid(), source.getFio(), source.getMail());
//        if(source.getRole() != null ){
//            user.setRole(EUserRole.valueOf(source.getRole()));
//        }
//        if(source.getStatus() != null){
//            user.setStatus(EUserStatus.valueOf(source.getStatus()));
//        }
//        if (source.getDt_create() != null){
//            user.setDt_create(LocalDateTime.ofInstant(Instant.ofEpochMilli(source.getDt_create()), ZoneOffset.UTC));
//        }
//        if(source.getDt_update() != null){
//            user.setDt_update(LocalDateTime.ofInstant(Instant.ofEpochMilli(source.getDt_update()), ZoneOffset.UTC));
//        }

        if(source.getRole() != null &&
           source.getStatus() != null &&
           source.getDt_create() != null &&
           source.getDt_update() != null
        ){
            user.setRole(EUserRole.valueOf(source.getRole()));
            user.setStatus(EUserStatus.valueOf(source.getStatus()));
            user.setDt_create(LocalDateTime.ofInstant(Instant.ofEpochMilli(source.getDt_create()), ZoneOffset.UTC));
            user.setDt_update(LocalDateTime.ofInstant(Instant.ofEpochMilli(source.getDt_update()), ZoneOffset.UTC));
            return user;
        } else {
            throw new ConversionException("ошибка конвертации из DTO в Entity");
        }
    }
}
