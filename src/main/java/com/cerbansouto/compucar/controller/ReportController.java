package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ServiceService;
import com.cerbansouto.compucar.api.UnauthorizedRequestException;
import com.cerbansouto.compucar.model.Service;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ServiceService serviceService;

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public String serviceReports() throws UnauthorizedRequestException {
        return "services/index";
    }

    @RequestMapping(value = "/services", method = RequestMethod.POST)
    public String queryServices(Model model, Request request) throws UnauthorizedRequestException {
        log.info("Requesting services for month {}", request.getParameter("month"));
        int month = Integer.parseInt(request.getParameter("month"));
        List<Service> services = serviceService.getForMonth(month);
        model.addAttribute("services", services);
        return "services/index";
    }
}

