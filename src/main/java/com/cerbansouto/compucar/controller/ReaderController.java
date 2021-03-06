package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ReaderService;
import com.cerbansouto.compucar.api.UnauthorizedRequestException;
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
    public List<Reader> list(@RequestParam(value = "delta", required = false) Integer delta) throws UnauthorizedRequestException {
        log.info("list");

        if (delta != null && delta > 0) {
            return service.list(delta);
        }

        return service.list();
    }

    @GetMapping(value = "/{code}")
    public Reader getReader(@PathVariable("code") String code) throws UnauthorizedRequestException {
        log.info("getReader {}", code);
        return service.fetch(code);
    }

    @PostMapping
    public Reader createReader(@RequestBody Reader reader) throws InvalidEntityException, UnauthorizedRequestException {
        log.info("createReader with  {}", reader);
        return service.create(reader);
    }

    @PutMapping
    public Reader updateReader(@RequestBody Reader reader) throws InvalidEntityException, UnauthorizedRequestException {
        log.info("updateReader with  {}", reader);
        return service.update(reader);
    }

    @PutMapping(value = "recharge/{code}")
    public void rechargeReader(@PathVariable("code") String code) throws UnauthorizedRequestException {
        log.info("rechargeReader {}", code);
        Reader reader = service.fetch(code);
        service.recharge(reader);
    }

    @DeleteMapping(value = "/{code}")
    public void deleteReader(@PathVariable("code") String code) throws UnauthorizedRequestException {
        log.info("deleteReader {}", code);
        Reader model = service.fetch(code);
        if (model != null) {
            service.delete(model);
        }
    }
}
