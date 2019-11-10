package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ServiceService;
import com.cerbansouto.compucar.api.UnauthorizedRequestException;
import com.cerbansouto.compucar.model.Service;
import com.cerbansouto.compucar.services.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/services")
public class ServiceController {

    @Autowired
    private ServiceService service;

    @PostMapping
    public Service createService(@RequestBody Service service) throws InvalidEntityException, UnauthorizedRequestException {
        log.info("createService with  {}", service);
        return this.service.create(service);
    }
}
