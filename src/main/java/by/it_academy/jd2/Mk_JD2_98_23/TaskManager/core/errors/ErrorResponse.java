package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.errors;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EErrorType;

public class ErrorResponse {
    private EErrorType logref;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(EErrorType logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public EErrorType getLogref() {
        return logref;
    }

    public void setLogref(EErrorType logref) {
        this.logref = logref;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
