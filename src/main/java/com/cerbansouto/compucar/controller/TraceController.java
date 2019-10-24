package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ServiceService;
import com.cerbansouto.compucar.api.TraceService;
import com.cerbansouto.compucar.model.Service;
import com.cerbansouto.compucar.model.Trace;
import com.cerbansouto.compucar.services.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/traces")
public class TraceController {

    @Autowired
    private TraceService service;

    @GetMapping
    public List<Trace> list(
            @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        log.info("list");
        return service.list(from, to, sort);
    }
}
