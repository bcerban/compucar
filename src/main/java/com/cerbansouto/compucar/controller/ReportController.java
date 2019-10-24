package com.cerbansouto.compucar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @RequestMapping(value = "/mechanic", method = RequestMethod.GET)
    public String mechanic() {
        return "reports/test";
    }
}
