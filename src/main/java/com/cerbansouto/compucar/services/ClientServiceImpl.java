package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.ClientService;
import com.cerbansouto.compucar.model.Client;
import com.cerbansouto.compucar.services.dataAccess.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository repository;

    public List<Client> list() {
        return repository.findAll();
    }

    public Client fetch(int number) {
        throw new NotImplementedException();
    }

    public Client create(Client client) {
        throw new NotImplementedException();
    }

    public Client update(Client client) {
        throw new NotImplementedException();
    }

    public void delete(Client client) {
        throw new NotImplementedException();
    }
}
