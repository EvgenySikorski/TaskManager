package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class LocalDateTimeToEpochConvertor implements Converter <LocalDateTime, Long> {

    @Override
    public Long convert(LocalDateTime source) {
        return ZonedDateTime.of(source, ZoneOffset.UTC).toInstant().toEpochMilli();
    }
}
