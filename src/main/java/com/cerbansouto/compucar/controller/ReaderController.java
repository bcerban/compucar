package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ReaderService;
import com.cerbansouto.compucar.model.Reader;
import com.cerbansouto.compucar.services.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/readers")
public class ReaderController {

    @Autowired
    private ReaderService service;

    @GetMapping
    public List<Reader> list(@RequestParam(value = "delta", required = false) Integer delta) {
        log.info("list");

        if (delta != null && delta > 0) {
            return service.list(delta);
        }

        return service.list();
    }

    @GetMapping(value = "/{id}")
    public Reader getReader(@PathVariable("id") Long id) {
        log.info("getReader {}", id);
        return service.fetch(id);
    }

    @PostMapping
    public Reader createReader(@RequestBody Reader reader) throws InvalidEntityException {
        log.info("createReader with  {}", reader);
        return service.create(reader);
    }

    @PutMapping
    public Reader updateReader(@RequestBody Reader reader) throws InvalidEntityException {
        log.info("updateReader with  {}", reader);
        return service.update(reader);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteReader(@PathVariable("id") Long id) {
        log.info("deleteReader {}", id);
        service.delete(id);
    }
}
