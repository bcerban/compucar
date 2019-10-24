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
        String code = "WKSP001";
        Workshop workshop = mock(Workshop.class);

        when(repository.getByCode(code)).thenReturn(workshop);
        assertEquals(workshop, workshopService.fetch(code));

        verify(repository, times(1)).getByCode(code);
    }

    @Test(expected = EntityNotFoundException.class)
    public void fetchNotFound() {
        String code = "WKSP001";

        when(repository.getByCode(code)).thenReturn(null);
        workshopService.fetch(code);

        verify(repository, times(1)).getByCode(code);
    }

    @Test(expected = EntityNotFoundException.class)
    public void fetchDeleted() {
        String code = "WKSP001";
        Workshop workshop = mock(Workshop.class);

        when(repository.getByCode(code)).thenReturn(workshop);
        when(workshop.isDeleted()).thenReturn(true);
        workshopService.fetch(code);

        verify(repository, times(1)).getByCode(code);
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
        String code = "WKSP001";
        Workshop workshop = new Workshop();
        workshop.setCode(code);

        Workshop existing = mock(Workshop.class);
        when(repository.getByCode(code)).thenReturn(existing);
        when(repository.update(existing)).thenThrow(new DataIntegrityViolationException(""));

        workshopService.update(workshop);

        verify(repository, times(1)).update(existing);
        verify(existing, times(0)).setCode(any());
    }

    @Test
    public void update() throws InvalidEntityException {
        String code = "WKSP001";
        Workshop workshop = new Workshop();
        workshop.setCode(code);

        Workshop existing = mock(Workshop.class);
        when(repository.getByCode(code)).thenReturn(existing);
        when(repository.update(existing)).thenReturn(existing);

        workshopService.update(workshop);

        verify(repository, times(1)).update(existing);
        verify(existing, times(0)).setCode(any());
    }

    @Test
    public void deleteWithExisting() {
        Workshop existing = mock(Workshop.class);
        workshopService.delete(existing);

        verify(repository, times(1)).update(existing);
        verify(existing, times(1)).setDeleted(true);
    }
}
