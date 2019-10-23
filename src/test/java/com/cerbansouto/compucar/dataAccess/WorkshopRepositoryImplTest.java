package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Workshop;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class WorkshopRepositoryImplTest {

    @Mock
    private Session session;

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private WorkshopRepositoryImpl repository;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll() {
        String queryString = "FROM Workshop w WHERE w.deleted = 0";
        Query query = mock(Query.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(queryString)).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList());

        repository.findAll();

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).createQuery(queryString);
        verify(query, times(1)).list();
    }

    @Test
    public void getById() {
        long id = 100;

        Workshop workshop = mock(Workshop.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.get(Workshop.class, id)).thenReturn(workshop);

        repository.getById(id);

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).get(Workshop.class, id);
    }

    @Test
    public void create() {
        long id = 100;

        Workshop workshop = mock(Workshop.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.save(workshop)).thenReturn(id);
        when(session.get(Workshop.class, id)).thenReturn(workshop);

        repository.create(workshop);

        verify(sessionFactory, times(2)).getCurrentSession();
        verify(session, times(1)).get(Workshop.class, id);
        verify(session, times(1)).save(workshop);
    }

    @Test
    public void update() {
        Workshop workshop = mock(Workshop.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        repository.update(workshop);

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).update(workshop);
    }

    @Test
    public void delete() {
        Workshop workshop = mock(Workshop.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        repository.delete(workshop);

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).delete(workshop);
    }
}
