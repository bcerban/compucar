package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ServiceError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ServiceErrorAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ServiceError unknownErrorHandler(Exception e) {
        ServiceError response = new ServiceError();
        response.setMessage(e.getMessage());
        return response;
    }
}
