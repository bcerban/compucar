package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Client;
import com.cerbansouto.compucar.services.dataAccess.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientRepositoryImpl implements ClientRepository {

    @Override
    public List<Client> findAll() {
        return new ArrayList<>();
    }
}
