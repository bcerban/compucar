package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ProfileService;
import com.cerbansouto.compucar.api.TraceService;
import com.cerbansouto.compucar.api.UnauthorizedRequestException;
import com.cerbansouto.compucar.model.ProfileReport;
import com.cerbansouto.compucar.model.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/profile-report")
public class ServiceProfileController {

    @Autowired
    private ProfileService service;

    @GetMapping
    public ProfileReport report(
            @RequestParam(value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date
    ) throws UnauthorizedRequestException {
        log.info("generateReport");
        return service.generate(date);
    }
}
