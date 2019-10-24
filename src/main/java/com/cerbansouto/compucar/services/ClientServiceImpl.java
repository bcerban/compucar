package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.ClientService;
import com.cerbansouto.compucar.model.Client;
import com.cerbansouto.compucar.services.dataAccess.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional
    @Override
    public List<Client> list() {
        log.info("###### Listing clients ######");
        return repository.findAll();
    }

    @Transactional
    @Override
    public Client fetch(Long number) {
        log.info(String.format("###### Fetching client with ID %d ######", number));
        Client found = repository.getById(number);

        if (found == null || found.isDeleted()) {
            throw new EntityNotFoundException(String.format("No client with ID %d", number));
        }

        return found;
    }

    @Transactional
    @Override
    public Client create(Client client) throws InvalidEntityException {
        log.info(String.format("###### Creating client with Email %s ######", client.getEmail()));
        validateNewClient(client);

        try {
            return repository.create(client);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not create client. Please check all information is correct.", e);
        }
    }

    @Transactional
    @Override
    public Client update(Client client) throws InvalidEntityException {
        log.info(String.format("###### Updating client with ID %d ######", client.getNumber()));
        validateClientUpdate(client);

        try {
            Client existingClient = fetch(client.getNumber());
            existingClient.setEmail(client.getEmail());
            existingClient.setName(client.getName());
            existingClient.setPhone(client.getPhone());
            existingClient.setType(client.getType());
            return repository.update(existingClient);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not update client. Please check all information is correct.", e);
        }
    }

    @Transactional
    @Override
    public void delete(Client model) {
        model.setDeleted(true);
        repository.update(model);
    }

    private void validateNewClient(Client client) throws InvalidEntityException {
        validateClientType(client.getType());
        validateClientNumber(client.getNumber());
    }

    private void validateClientUpdate(Client client) throws InvalidEntityException {
        validateClientType(client.getType());
        validateClientNumber(client.getNumber());
    }

    private void validateClientType(String type) throws InvalidEntityException {
        if (type == null ||
                !(TYPE_PERSON.equals(type.toUpperCase()) || TYPE_COMPANY.equals(type.toUpperCase()))) {
            throw new InvalidEntityException(String.format("Type provided for client (%s) is not valid.", type));
        }
    }

    private void validateClientNumber(Long number) throws InvalidEntityException {
        if (number == null || number == 0) {
            throw new InvalidEntityException("Client number cannot be empty.");
        }
    }
}
