package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.config;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserDTOToUserConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserToUserDTOConvertor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserServiceConfig {
    @Bean
    public UserToUserDTOConvertor userToUserDTOConvertor(){
        return new UserToUserDTOConvertor();
    }
    @Bean
    public UserDTOToUserConvertor userDTOToUserConvertor(){
        return new UserDTOToUserConvertor();
    }
}
