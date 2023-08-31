package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Objects;

@AllArgsConstructor

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

    private final ConversionService conversionService;

    @Override
    @NonNull
    public LocalDateTime parse(@NonNull String text, @NonNull Locale locale) {
        return Objects.requireNonNull(conversionService.convert(Long.valueOf(text), LocalDateTime.class));
    }

    @Override
    @NonNull
    public String print(LocalDateTime object, @NonNull Locale locale) {
        return String.valueOf(object.toEpochSecond(ZoneOffset.UTC));
    }

//    @Override
//    public LocalDateTime parse(String text, Locale locale) throws ParseException {
//        long milliseconds = Long.parseLong(text);
//        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
//    }
//
//    @Override
//    public String print(LocalDateTime object, Locale locale) {
//        return object.toString();
//    }
}
