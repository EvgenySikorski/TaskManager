package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.config;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.EpochToLocalDateTimeConverter;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.LocalDateTimeFormatter;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.LocalDateTimeToEpochConverter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void addFormatters(FormatterRegistry registry) {

        ConversionService conversionService = (ConversionService) registry;

        registry.addConverter(new EpochToLocalDateTimeConverter());
        registry.addConverter(new LocalDateTimeToEpochConverter());
        registry.addFormatter(new LocalDateTimeFormatter(conversionService));

    }
}