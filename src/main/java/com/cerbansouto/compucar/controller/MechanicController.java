package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.MechanicService;
import com.cerbansouto.compucar.model.Mechanic;
import com.cerbansouto.compucar.services.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/mechanics")
public class MechanicController {

    @Autowired
    private MechanicService service;

    @GetMapping
    public List<Mechanic> getAll() {
        return service.list();
    }

    @GetMapping(value = "/{id}")
    public Mechanic get(@PathVariable("id") long id) {
        return service.fetch(id);
    }

    @PostMapping
    public Mechanic create(@RequestBody Mechanic mechanic) throws InvalidEntityException {
        return service.create(mechanic);
    }

    @PutMapping
    public Mechanic update(@RequestBody Mechanic mechanic) throws InvalidEntityException {
        return service.update(mechanic);
    }

    @DeleteMapping(value = "{id}")
    public void deleteMechanic(@PathVariable("id") long id) {
        log.info("deleteMechanic {}", id);
        Mechanic model = service.fetch(id);
        if (model != null) {
            service.delete(model);
        }
    }
}
