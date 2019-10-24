package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.MechanicService;
import com.cerbansouto.compucar.model.Mechanic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/mechanics")
public class MechanicJspController {

    @Autowired
    private MechanicService mechanicService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listMechanics(Model model) {
        List<Mechanic> mechanics = mechanicService.list();
        model.addAttribute("mechanics", mechanics);
        return "mechanics/index";
    }
}
