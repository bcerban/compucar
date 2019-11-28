package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.EventDiagnosisService;
import com.cerbansouto.compucar.api.ServiceService;
import com.cerbansouto.compucar.api.UnauthorizedRequestException;
import com.cerbansouto.compucar.model.EventDiagnosis;
import com.cerbansouto.compucar.model.Service;
import com.cerbansouto.compucar.services.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/services")
public class ServiceController {

    @Autowired
    private ServiceService service;

    @Autowired
    private EventDiagnosisService diagnosisService;

    @GetMapping
    public List<Service> listServices(
            @RequestParam(value = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(value = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to
    ) {
        log.info("listServices from {} to {}", from, to);
        return service.getForRange(from, to);
    }

    @GetMapping(value = "/{serviceCode}")
    public Service getService(@PathVariable String serviceCode) {
        log.info("getService with  {}", serviceCode);
        return service.fetch(serviceCode);
    }

    @PostMapping
    public Service createService(@RequestBody Service service) throws InvalidEntityException, UnauthorizedRequestException {
        log.info("createService with  {}", service);
        return this.service.create(service);
    }

    @PostMapping(value = "/{serviceCode}/diagnoses")
    public EventDiagnosis addDiagnosisToService(@PathVariable String serviceCode, @RequestBody EventDiagnosis diagnosis) throws InvalidEntityException, UnauthorizedRequestException {
        log.info("addDiagnosisToService with  {}", diagnosis);
        Service found = service.fetch(serviceCode);
        diagnosis.setService(found);
        return diagnosisService.create(diagnosis);
    }
}
