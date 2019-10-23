package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping(value = "/clients")
public class ClientWebController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String list(Model model) {
        log.info("list");
        model.addAttribute("clients", clientService.list());
        return "client/list";
    }
}
