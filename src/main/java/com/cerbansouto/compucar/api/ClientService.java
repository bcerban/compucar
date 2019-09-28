package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.Client;
import java.util.List;

public interface ClientService {

    List<Client> list();
    Client fetch(int number);
    Client create(Client client);
    Client update(Client client);
    void delete(Client client);
}
