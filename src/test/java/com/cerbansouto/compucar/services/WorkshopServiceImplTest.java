package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.model.Workshop;
import com.cerbansouto.compucar.services.dataAccess.WorkshopRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class WorkshopServiceImplTest {
    @Mock
    private WorkshopRepository repository;

    @InjectMocks
    private WorkshopServiceImpl workshopService;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void list() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        workshopService.list();

        verify(repository, times(1)).findAll();
    }

    @Test
    public void fetchFound() {
        long id = 100;
        Workshop workshop = mock(Workshop.class);

        when(repository.getById(id)).thenReturn(workshop);
        assertEquals(workshop, workshopService.fetch(id));

        verify(repository, times(1)).getById(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void fetchNotFound() {
        long id = 100;

        when(repository.getById(id)).thenReturn(null);
        workshopService.fetch(id);

        verify(repository, times(1)).getById(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void fetchDeleted() {
        long id = 100;
        Workshop workshop = mock(Workshop.class);

        when(repository.getById(id)).thenReturn(workshop);
        when(workshop.isDeleted()).thenReturn(true);
        workshopService.fetch(id);

        verify(repository, times(1)).getById(id);
    }

    @Test(expected = InvalidEntityException.class)
    public void createWithNullCode() throws InvalidEntityException {
        Workshop workshop = new Workshop();
        workshopService.create(workshop);

        verify(repository, times(0)).create(workshop);
    }

    @Test(expected = InvalidEntityException.class)
    public void createWithEmptyCode() throws InvalidEntityException {
        Workshop workshop = new Workshop();
        workshop.setCode("");
        workshopService.create(workshop);

        verify(repository, times(0)).create(workshop);
    }

    @Test(expected = InvalidEntityException.class)
    public void createWithDataIntegrityConstraint() throws InvalidEntityException {
        Workshop workshop = new Workshop();
        workshop.setCode("test_code");

        when(repository.create(workshop)).thenThrow(new DataIntegrityViolationException(""));
        workshopService.create(workshop);

        verify(repository, times(1)).create(workshop);
    }

    @Test
    public void create() throws InvalidEntityException {
        Workshop workshop = new Workshop();
        workshop.setCode("test_code");

        when(repository.create(workshop)).thenReturn(workshop);
        workshopService.create(workshop);

        verify(repository, times(1)).create(workshop);
    }

    @Test(expected = InvalidEntityException.class)
    public void updateWithNullCode() throws InvalidEntityException {
        Workshop workshop = new Workshop();
        workshopService.update(workshop);

        verify(repository, times(0)).update(any(Workshop.class));
    }

    @Test(expected = InvalidEntityException.class)
    public void updateWithEmptyCode() throws InvalidEntityException {
        Workshop workshop = new Workshop();
        workshop.setCode("");
        workshopService.update(workshop);

        verify(repository, times(0)).update(any(Workshop.class));
    }

    @Test(expected = InvalidEntityException.class)
    public void updateWithDataIntegrityViolation() throws InvalidEntityException {
        long id = 100;
        Workshop workshop = new Workshop();
        workshop.setId(id);
        workshop.setCode("test_code");

        Workshop existing = mock(Workshop.class);
        when(repository.getById(id)).thenReturn(existing);
        when(repository.update(existing)).thenThrow(new DataIntegrityViolationException(""));

        workshopService.update(workshop);

        verify(repository, times(1)).update(existing);
        verify(existing, times(0)).setCode(any());
    }

    @Test
    public void update() throws InvalidEntityException {
        long id = 100;
        Workshop workshop = new Workshop();
        workshop.setId(id);
        workshop.setCode("test_code");

        Workshop existing = mock(Workshop.class);
        when(repository.getById(id)).thenReturn(existing);
        when(repository.update(existing)).thenReturn(existing);

        workshopService.update(workshop);

        verify(repository, times(1)).update(existing);
        verify(existing, times(0)).setCode(any());
    }

    @Test
    public void deleteWithExisting() {
        long id = 100;
        Workshop existing = mock(Workshop.class);

        when(repository.getById(id)).thenReturn(existing);
        workshopService.delete(id);

        verify(repository, times(1)).update(existing);
        verify(existing, times(1)).setDeleted(true);
    }

    @Test
    public void deleteWithoutExisting() {
        long id = 100;

        when(repository.getById(id)).thenReturn(null);
        workshopService.delete(id);

        verify(repository, times(0)).update(any(Workshop.class));
    }
}
