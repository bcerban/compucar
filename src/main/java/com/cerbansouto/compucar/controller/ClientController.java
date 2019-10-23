package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ClientService;
import com.cerbansouto.compucar.model.Client;
import com.cerbansouto.compucar.services.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> list() {
        log.info("list");
        return clientService.list();
    }

    @GetMapping(value = "/{id}")
    public Client getClient(@PathVariable("id") Long id) {
        log.info("getClient {}", id);
        return clientService.fetch(id);
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) throws InvalidEntityException {
        log.info("createClient with  {}", client);
        return clientService.create(client);
    }

    @PutMapping
    public Client updateClient(@RequestBody Client client) throws InvalidEntityException {
        log.info("updateClient with  {}", client);
        return clientService.update(client);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteClient(@PathVariable("id") Long id) {
        log.info("deleteWorkshop {}", id);
        clientService.delete(id);
    }
}
