package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ServiceService;
import com.cerbansouto.compucar.model.Service;
import org.eclipse.jetty.server.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ServiceService serviceService;

    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public String serviceReports() {
        return "reports/service";
    }

    @RequestMapping(value = "/queryReports", method = RequestMethod.POST)
    public String queryServices(Model model, Request request) {
        long month = Long.parseLong(request.getParameter("month"));
        List<Service> services = this.serviceService.getForMonth(month);
        model.addAttribute("services", services);
        return "services/index";
    }
}

