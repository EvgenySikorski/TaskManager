package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions;

import java.util.Map;

public class NotValidTaskCreatDtoBodyException extends RuntimeException{

    private Map<String, String> errors;

    public NotValidTaskCreatDtoBodyException(Map<String, String> errors) {
        this.errors = errors;
    }

    public NotValidTaskCreatDtoBodyException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public NotValidTaskCreatDtoBodyException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause);
        this.errors = errors;
    }

    public NotValidTaskCreatDtoBodyException(Throwable cause, Map<String, String> errors) {
        super(cause);
        this.errors = errors;
    }

    public NotValidTaskCreatDtoBodyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
