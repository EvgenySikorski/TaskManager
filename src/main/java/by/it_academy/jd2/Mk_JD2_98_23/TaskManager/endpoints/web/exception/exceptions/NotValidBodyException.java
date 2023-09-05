package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions;

import java.util.Map;

public class NotValidBodyException extends RuntimeException{

    private Map<String, String> errors;

    public NotValidBodyException(Map<String, String> errors) {
        this.errors = errors;
    }

    public NotValidBodyException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public NotValidBodyException(String message, Throwable cause, Map<String, String> errors) {
        super(message, cause);
        this.errors = errors;
    }

    public NotValidBodyException(Throwable cause, Map<String, String> errors) {
        super(cause);
        this.errors = errors;
    }

    public NotValidBodyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map<String, String> errors) {
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
