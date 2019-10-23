package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.MechanicService;
import com.cerbansouto.compucar.model.Mechanic;
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
        return this.mechanicService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Mechanic get(@PathVariable("id") long id) {
        return this.mechanicService.get(id);
    }

    @PostMapping
    public Mechanic create(@RequestBody Mechanic mechanic) {
        return this.mechanicService.create(mechanic);
    }

    @PutMapping(value = "/{id}")
    public Mechanic update(@PathVariable("id") long id, @RequestBody Mechanic mechanic) {
        return this.mechanicService.update(id, mechanic);
    }

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable("id") long id) {
        this.mechanicService.delete(id);
    }
}
