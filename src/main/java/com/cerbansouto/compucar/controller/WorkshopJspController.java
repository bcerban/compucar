package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.WorkshopService;
import com.cerbansouto.compucar.model.Workshop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/workshops")
public class WorkshopJspController {

    @Autowired
    private WorkshopService workshopService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listWorkshops(Model model) {
        List<Workshop> workshops = workshopService.list();
        model.addAttribute("workshops", workshops);
        return "workshops/index";
    }
}
