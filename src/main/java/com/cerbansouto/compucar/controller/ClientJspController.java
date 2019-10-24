package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ClientService;
import com.cerbansouto.compucar.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientJspController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listClients(Model model) {
        List<Client> clients = clientService.list();
        model.addAttribute("clients", clients);
        return "clients/index";
    }
}
