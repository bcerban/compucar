package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ServiceError;
import com.cerbansouto.compucar.services.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@Slf4j
@ControllerAdvice
public class ServiceErrorAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ServiceError invalidEntityHandler(InvalidEntityException e) {
        return processException(e);
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ServiceError entityNotFoundHandler(EntityNotFoundException e) {
        return processException(e);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ServiceError unknownErrorHandler(Exception e) {
        return processException(e);
    }

    private ServiceError processException(Exception e) {
        log.error(e.getMessage(), e);
        ServiceError response = new ServiceError();
        response.setMessage(e.getMessage());
        return response;
    }
}
