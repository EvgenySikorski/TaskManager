package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.serializer;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.errors.ErrorResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;

public class ErrorResponseSerializer extends JsonObjectSerializer<ErrorResponse> {
    @Override
    protected void serializeObject(ErrorResponse value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStringField("logref",value.getLogref().name().toLowerCase());
        jgen.writeStringField("message", value.getMessage());
    }
}
