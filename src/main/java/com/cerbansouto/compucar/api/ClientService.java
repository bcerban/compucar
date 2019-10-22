package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.Client;
import com.cerbansouto.compucar.services.InvalidEntityException;

import java.util.List;

public interface ClientService {

    String TYPE_PERSON = "PERSON";
    String TYPE_COMPANY = "COMPANY";

    List<Client> list();
    Client fetch(Long number);
    Client create(Client client) throws InvalidEntityException;
    Client update(Client client) throws InvalidEntityException;
    void delete(Client client);
    void delete(Long number);
}
