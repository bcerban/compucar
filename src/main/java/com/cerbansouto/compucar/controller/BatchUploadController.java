package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.*;
import com.cerbansouto.compucar.model.*;
import com.cerbansouto.compucar.services.InvalidEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/batch")
public class BatchUploadController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private MechanicService mechanicService;

    @Autowired
    private WorkshopService workshopService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private ServiceService serviceService;

    @PostMapping
    public BatchUploadResult createBatch(@RequestBody BatchUploadRequest batch) {
        log.info("createBatch with");

        List<String> messages = new ArrayList<>();

        batch.getClients().forEach(client -> {
            try {
                clientService.create(client);
            } catch (InvalidEntityException e) {
                messages.add(String.format("Failed to add client %d: %s", client.getNumber(), e.getMessage()));
            }
        });

        batch.getMechanics().forEach(mechanic -> {
            try {
                mechanicService.create(mechanic);
            } catch (InvalidEntityException e) {
                messages.add(String.format("Failed to add mechanic %d: %s", mechanic.getNumber(), e.getMessage()));
            }
        });

        batch.getWorkshops().forEach(workshop -> {
            try {
                workshopService.create(workshop);
            } catch (InvalidEntityException e) {
                messages.add(String.format("Failed to add workshop %s: %s", workshop.getCode(), e.getMessage()));
            }
        });

        batch.getReaders().forEach(reader -> {
            try {
                readerService.create(reader);
            } catch (InvalidEntityException e) {
                messages.add(String.format("Failed to add reader %s: %s", reader.getCode(), e.getMessage()));
            }
        });

        batch.getServices().forEach(service -> {
            try {
                serviceService.create(service);
            } catch (InvalidEntityException e) {
                messages.add(String.format("Failed to add service %s: %s", service.getCode(), e.getMessage()));
            }
        });

        BatchUploadResult result = new BatchUploadResult();
        result.setMessages(messages);
        result.setSuccess(messages.isEmpty());
        return result;
    }
}
