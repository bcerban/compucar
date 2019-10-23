package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.ClientService;
import com.cerbansouto.compucar.model.Client;
import com.cerbansouto.compucar.services.dataAccess.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void list() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        clientService.list();

        verify(repository, times(1)).findAll();
    }

    @Test
    public void fetchFound() {
        long clientId = 100;
        Client client = mock(Client.class);

        when(repository.getById(clientId)).thenReturn(client);
        assertEquals(client, clientService.fetch(clientId));

        verify(repository, times(1)).getById(clientId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void fetchNotFound() {
        long clientId = 100;

        when(repository.getById(clientId)).thenReturn(null);
        clientService.fetch(clientId);

        verify(repository, times(1)).getById(clientId);
    }

    @Test(expected = EntityNotFoundException.class)
    public void fetchDeleted() {
        long clientId = 100;
        Client client = mock(Client.class);

        when(repository.getById(clientId)).thenReturn(client);
        when(client.isDeleted()).thenReturn(true);
        clientService.fetch(clientId);

        verify(repository, times(1)).getById(clientId);
    }

    @Test(expected = InvalidEntityException.class)
    public void createNullType() throws InvalidEntityException {
        Client client = new Client();
        clientService.create(client);

        verify(repository, times(0)).create(client);
    }

    @Test(expected = InvalidEntityException.class)
    public void createWithInvalidType() throws InvalidEntityException {
        Client client = new Client();
        client.setType("PERSONAL");
        clientService.create(client);

        verify(repository, times(0)).create(client);
    }

    @Test(expected = InvalidEntityException.class)
    public void createWithNullNumber() throws InvalidEntityException {
        Client client = new Client();
        client.setType(ClientService.TYPE_PERSON);
        clientService.create(client);

        verify(repository, times(0)).create(client);
    }

    @Test(expected = InvalidEntityException.class)
    public void createWithEmptyNumber() throws InvalidEntityException {
        Client client = new Client();
        client.setType(ClientService.TYPE_PERSON);
        client.setNumber((long) 0);
        clientService.create(client);

        verify(repository, times(0)).create(client);
    }

    @Test(expected = InvalidEntityException.class)
    public void createDataIntegrityViolation() throws InvalidEntityException {
        Client client = new Client();
        client.setType(ClientService.TYPE_PERSON);
        client.setNumber((long) 1234);

        doThrow(new DataIntegrityViolationException("")).when(repository).create(client);
        clientService.create(client);

        verify(repository, times(1)).create(client);
    }

    @Test
    public void create() throws InvalidEntityException {
        Client client = new Client();
        client.setType(ClientService.TYPE_PERSON);
        client.setNumber((long) 1234);

        when(repository.create(client)).thenReturn(client);
        clientService.create(client);

        verify(repository, times(1)).create(client);
    }

    @Test(expected = InvalidEntityException.class)
    public void updateWithNullType() throws InvalidEntityException {
        Client client = new Client();
        client.setId((long) 100);
        clientService.update(client);

        verify(repository, times(0)).update(client);
    }

    @Test(expected = InvalidEntityException.class)
    public void updateWithDataIntegrityViolation() throws InvalidEntityException {
        long clientId = 100;
        long clientNumber = 1234;
        String clientEmail = "test@email.com";

        Client client = new Client();
        client.setId(clientId);
        client.setNumber(clientNumber);
        client.setEmail(clientEmail);
        client.setPhone("");
        client.setType(ClientService.TYPE_PERSON);

        Client existingClient = mock(Client.class);
        when(repository.getById(clientId)).thenReturn(existingClient);
        when(repository.update(existingClient)).thenThrow(new DataIntegrityViolationException(""));

        clientService.update(client);

        verify(repository, times(1)).update(existingClient);
        verify(existingClient, times(0)).setNumber(clientNumber);
        verify(existingClient, times(1)).setEmail(clientEmail);
        verify(existingClient, times(1)).setPhone("");
        verify(existingClient, times(1)).setType(ClientService.TYPE_PERSON);
    }

    @Test
    public void update() throws InvalidEntityException {
        long clientId = 100;
        long clientNumber = 1234;
        String clientEmail = "test@email.com";

        Client client = new Client();
        client.setId(clientId);
        client.setNumber(clientNumber);
        client.setEmail(clientEmail);
        client.setPhone("");
        client.setType(ClientService.TYPE_PERSON);

        Client existingClient = mock(Client.class);
        when(repository.getById(clientId)).thenReturn(existingClient);

        clientService.update(client);

        verify(repository, times(1)).update(existingClient);
        verify(existingClient, times(0)).setNumber(clientNumber);
        verify(existingClient, times(1)).setEmail(clientEmail);
        verify(existingClient, times(1)).setPhone("");
        verify(existingClient, times(1)).setType(ClientService.TYPE_PERSON);
    }

    @Test
    public void deleteWithExisting() {
        long clientId = 100;
        Client existingClient = mock(Client.class);

        when(repository.getById(clientId)).thenReturn(existingClient);
        clientService.delete(clientId);

        verify(repository, times(1)).update(existingClient);
        verify(existingClient, times(1)).setDeleted(true);
    }

    @Test
    public void deleteWithoutExisting() {
        long clientId = 100;

        when(repository.getById(clientId)).thenReturn(null);
        clientService.delete(clientId);

        verify(repository, times(0)).update(any(Client.class));
    }
}
