package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class EpochToLocalDateTimeConvertor implements Converter<Long, LocalDateTime> {
    @Override
    public LocalDateTime convert(Long source) {
        return Instant.ofEpochMilli(source)
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime()
                .truncatedTo(ChronoUnit.MILLIS);
    }
}
