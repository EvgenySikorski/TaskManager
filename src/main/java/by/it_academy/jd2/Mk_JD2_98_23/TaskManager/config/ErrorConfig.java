package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.config;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.errors.ErrorResponse;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.errors.StructuredErrorResponse;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.serializer.ErrorResponseSerializer;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.serializer.StructuredErrorResponseSerializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ErrorConfig {

    @Bean
    public StructuredErrorResponseSerializer structuredErrorResponseSerializer() {
        return new StructuredErrorResponseSerializer();
    }

    @Bean
    public ErrorResponseSerializer errorResponseSerializer() {
        return new ErrorResponseSerializer();
    }
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder(StructuredErrorResponseSerializer structuredErrorResponseSerializer, ErrorResponseSerializer errorResponseSerializer) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        builder.serializerByType(StructuredErrorResponse.class, structuredErrorResponseSerializer);
        builder.serializerByType(ErrorResponse.class, errorResponseSerializer);
        //builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return builder;
    }
}
