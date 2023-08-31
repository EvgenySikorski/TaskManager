package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDetailsImpl;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserStatus;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserToUserDetailsConvertor implements Converter<User, UserDetailsImpl> {
    @Override
    public UserDetailsImpl convert(User source) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUuid(source.getUuid());
        userDetails.setFio(source.getFio());
        userDetails.setUsername(source.getMail());
        userDetails.setRole(source.getRole());
        userDetails.setStatus(source.getStatus());
        userDetails.setAccountNonLocked(source.getStatus().equals(EUserStatus.ACTIVATED));
        List<GrantedAuthority> authority = List.of(new SimpleGrantedAuthority(source.getRole().getUserRole()));
        userDetails.setAuthorities(authority);
        return userDetails;
    }
}
