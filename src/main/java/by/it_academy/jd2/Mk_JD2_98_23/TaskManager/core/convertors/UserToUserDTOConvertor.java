package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;

public class UserToUserDTOConvertor implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User source) {
        UserDTO userDTO = new UserDTO(source.getUuid(), source.getMail(), source.getFio(), source.getRole().name(), source.getStatus().name());
        if(source.getDt_create() != null){
            userDTO.setDtCreate(source.getDt_create().toInstant(ZoneOffset.UTC).toEpochMilli());
        }
        if(source.getDt_update() != null){
            userDTO.setDtUpdate(source.getDt_update().toInstant(ZoneOffset.UTC).toEpochMilli());
        }
        return userDTO;    }

}
