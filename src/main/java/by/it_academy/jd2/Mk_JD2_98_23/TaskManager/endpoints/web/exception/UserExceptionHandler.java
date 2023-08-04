package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EErrorType;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.errors.ErrorResponse;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.errors.StructuredErrorResponse;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions.*;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StructuredErrorResponse> handleInvalidArgument(ConstraintViolationException violationException){
        StructuredErrorResponse response = new StructuredErrorResponse(EErrorType.STRUCTURED_ERROR, new HashMap<>());
        violationException.getConstraintViolations().stream().forEach(violation -> {
            response.getErrors().put(violation.getPropertyPath().toString(), violation.getMessage());
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(){
        ErrorResponse response = new ErrorResponse(EErrorType.ERROR, "Запрос содержит некорректные данные. Измените запрос и отправьте его еще раз");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({
            ConversionException.class,
            IllegalArgumentException.class,
            IOException.class,
            IndexOutOfBoundsException.class,
            ArithmeticException.class,
            Error.class
    })
    public ResponseEntity<ErrorResponse> handleInnerError(){
        ErrorResponse response = new ErrorResponse(EErrorType.ERROR, "Внутренняя ошибка сервера. " +
                "Сервер не смог корректно обработать запрос. Пожалуйста обратитесь к администратору");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MailAlreadyExistsException.class)
    public ResponseEntity<StructuredErrorResponse> handleMailError(MailAlreadyExistsException exception){
        StructuredErrorResponse response = new StructuredErrorResponse(EErrorType.STRUCTURED_ERROR, new HashMap<>());
        response.getErrors().put("email", "Пользователь с такой почтой уже существует: " + exception.getEmail());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFountError(UserNotFoundException exception){
        ErrorResponse response = new ErrorResponse(EErrorType.ERROR, "Пользователь с id " + exception.getUuid() + "не найден");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuditNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuditNotFoundException(AuditNotFoundException exception){
        ErrorResponse response = new ErrorResponse(EErrorType.ERROR, "Аудит с id " + exception.getUuid() + "не найден");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VersionException.class)
    public ResponseEntity<ErrorResponse> handleVersionError(VersionException exception){
        ErrorResponse response = new ErrorResponse(EErrorType.ERROR, "Текущая версия не совпадает с указанной");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
