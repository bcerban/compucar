package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.ClientService;
import com.cerbansouto.compucar.model.Client;
import com.cerbansouto.compucar.services.InvalidEntityException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ClientControllerTest {

    @Mock
    private ClientService service;

    @InjectMocks
    private ClientController controller;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void list() {
        controller.list();
        verify(service, times(1)).list();
    }

    @Test
    public void getClient() {
        long id = 100;
        controller.getClient(id);
        verify(service, times(1)).fetch(id);
    }

    @Test
    public void createClient() throws InvalidEntityException {
        Client client = mock(Client.class);
        controller.createClient(client);
        verify(service, times(1)).create(client);
    }

    @Test
    public void updateClient() throws InvalidEntityException {
        Client client = mock(Client.class);
        controller.updateClient(client);
        verify(service, times(1)).update(client);
    }

    @Test
    public void delete() {
        long clientId = 100;
        Client existingClient = mock(Client.class);

        when(service.fetch(clientId)).thenReturn(existingClient);
        controller.deleteClient(clientId);
        verify(service, times(1)).delete(existingClient);
    }
}
