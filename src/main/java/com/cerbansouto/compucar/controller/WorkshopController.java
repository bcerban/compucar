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

    @GetMapping(value = "/{id}")
    public Workshop getWorkshop(@PathVariable("id") Long id) {
        log.info("getWorkshop {}", id);
        return service.fetch(id);
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

    @DeleteMapping(value = "/{id}")
    public void deleteWorkshop(@PathVariable("id") Long id) {
        log.info("deleteWorkshop {}", id);
        service.delete(id);
    }
}
