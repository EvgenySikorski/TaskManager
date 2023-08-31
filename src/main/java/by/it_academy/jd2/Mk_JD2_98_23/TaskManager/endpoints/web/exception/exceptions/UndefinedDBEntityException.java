package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions;

import org.springframework.dao.DataAccessException;

public class UndefinedDBEntityException extends DataAccessException {
    public UndefinedDBEntityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
