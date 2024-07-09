package com.musicshop.error;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Hidden
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {HttpMessageNotReadableException.class, ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDescription badRequest(RuntimeException ex) {
        return new ErrorDescription(ex.getClass().getName(), ex.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDescription badRequestArgument(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errorMessages.add(fieldError.getDefaultMessage());
        }
        return new ErrorDescription(ex.getClass().getName(), errorMessages.toString());
    }

    @ExceptionHandler(value = {BadCredentialsException.class, InternalAuthenticationServiceException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorDescription badCredentials(RuntimeException ex) {
        log.info("Не удалось авторизоваться", ex);
        return new ErrorDescription(ex.getClass().getName(), ex.getMessage());
    }

    @ExceptionHandler(value = {AccessForbiddenException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorDescription resourceForbidden(RuntimeException ex) {
        return new ErrorDescription(ex.getClass().getName(), ex.getMessage());
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDescription notFound(RuntimeException ex) {
        return new ErrorDescription(ex.getClass().getName(), ex.getMessage());
    }

    @ExceptionHandler(value = {OccupiedLoginException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorDescription conflict(RuntimeException ex) {
        return new ErrorDescription(ex.getClass().getName(), ex.getMessage());
    }

    @ExceptionHandler(value = {ConnectException.class})
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorDescription connectException(RuntimeException ex) {
        return new ErrorDescription(ex.getClass().getName(), ex.getMessage());
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(value = HttpStatus.BAD_GATEWAY)
    public ErrorDescription unknownException(RuntimeException ex) {
        return new ErrorDescription(ex.getClass().getName(), ex.getMessage());
    }
}
