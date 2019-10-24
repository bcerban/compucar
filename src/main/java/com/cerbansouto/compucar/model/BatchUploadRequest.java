package com.cerbansouto.compucar.model;

import lombok.Data;

import java.util.List;

@Data
public class BatchUploadRequest {
    private List<Client> clients;
    private List<Mechanic> mechanics;
    private List<Workshop> workshops;
    private List<Reader> readers;
    private List<Service> services;
}
