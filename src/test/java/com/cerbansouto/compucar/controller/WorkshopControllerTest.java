package com.cerbansouto.compucar.controller;

import com.cerbansouto.compucar.api.WorkshopService;
import com.cerbansouto.compucar.model.Workshop;
import com.cerbansouto.compucar.services.InvalidEntityException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class WorkshopControllerTest {

    @Mock
    private WorkshopService service;

    @InjectMocks
    private WorkshopController controller;

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
    public void getWorkshop() {
        String code = "WKSP001";
        controller.getWorkshop(code);
        verify(service, times(1)).fetch(code);
    }

    @Test
    public void createClient() throws InvalidEntityException {
        Workshop workshop = mock(Workshop.class);
        controller.createWorkshop(workshop);
        verify(service, times(1)).create(workshop);
    }

    @Test
    public void updateClient() throws InvalidEntityException {
        Workshop workshop = mock(Workshop.class);
        controller.updateWorkshop(workshop);
        verify(service, times(1)).update(workshop);
    }

    @Test
    public void delete() {
        String code = "WKSP001";
        Workshop existing = mock(Workshop.class);

        when(service.fetch(code)).thenReturn(existing);
        controller.deleteWorkshop(code);
        verify(service, times(1)).delete(existing);
    }
}
