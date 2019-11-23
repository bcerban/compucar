package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/traces")
public class TraceJspController {
    @Autowired
    private TraceService service;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("traces", service.list());
        return "traces/index";
    }
}
