package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.MechanicService;
import com.cerbansouto.compucar.model.Mechanic;
import com.cerbansouto.compucar.services.InvalidEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/mechanics")
public class MechanicController {

    @Autowired
    private MechanicService mechanicService;

    @GetMapping
    public List<Mechanic> getAll() {
        return this.mechanicService.list();
    }

    @GetMapping(value = "/{id}")
    public Mechanic get(@PathVariable("id") long id) {
        return this.mechanicService.fetch(id);
    }

    @PostMapping
    public Mechanic create(@RequestBody Mechanic mechanic) throws InvalidEntityException {
        return this.mechanicService.create(mechanic);
    }

    @PutMapping
    public Mechanic update(@RequestBody Mechanic mechanic) throws InvalidEntityException {
        return this.mechanicService.update(mechanic);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable("id") long id) {
        this.mechanicService.delete(id);
    }
}
