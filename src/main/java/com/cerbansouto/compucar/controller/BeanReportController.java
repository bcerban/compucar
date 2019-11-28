package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.BeanReporterService;
import com.cerbansouto.compucar.api.UnauthorizedRequestException;
import com.cerbansouto.compucar.model.BeanInitializationReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/beanReports")
public class BeanReportController {

    @Autowired
    private BeanReporterService beanReporterService;

    @GetMapping
    public List<BeanInitializationReport> list() throws UnauthorizedRequestException {
        return beanReporterService.findAll();
    }
}
