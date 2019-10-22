package com.cerbansouto.compucar.services.dataAccess;

import com.cerbansouto.compucar.model.Client;

import java.util.List;

public interface ClientRepository
{
    List<Client> findAll();
    Client getById(long id);
    Client create(Client client);
    Client update(Client client);
    void delete(Client client);
}
