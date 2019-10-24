package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.WorkshopService;
import com.cerbansouto.compucar.model.Client;
import com.cerbansouto.compucar.model.Workshop;
import com.cerbansouto.compucar.services.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/workshops")
public class WorkshopController {

    @Autowired
    private WorkshopService service;

    @GetMapping
    public List<Workshop> list() {
        log.info("list");
        return service.list();
    }

    @GetMapping(value = "/{code}")
    public Workshop getWorkshop(@PathVariable("code") String code) {
        log.info("getWorkshop {}", code);
        return service.fetch(code);
    }

    @PostMapping
    public Workshop createWorkshop(@RequestBody Workshop workshop) throws InvalidEntityException {
        log.info("createWorkshop with  {}", workshop);
        return service.create(workshop);
    }

    @PutMapping
    public Workshop updateWorkshop(@RequestBody Workshop workshop) throws InvalidEntityException {
        log.info("updateClient with  {}", workshop);
        return service.update(workshop);
    }

    @DeleteMapping(value = "/{code}")
    public void deleteWorkshop(@PathVariable("code") String code) {
        log.info("deleteWorkshop {}", code);
        Workshop model = service.fetch(code);
        if (model != null) {
            service.delete(model);
        }
    }
}
