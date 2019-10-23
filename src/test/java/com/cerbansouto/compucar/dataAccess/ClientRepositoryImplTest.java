package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Client;
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

public class ClientRepositoryImplTest {

    @Mock
    private Session session;

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private ClientRepositoryImpl repository;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll() {
        String queryString = "FROM Client c WHERE c.deleted = 0";
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

        Client client = mock(Client.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.get(Client.class, id)).thenReturn(client);

        repository.getById(id);

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).get(Client.class, id);
    }

    @Test
    public void create() {
        long id = 100;

        Client client = mock(Client.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.save(client)).thenReturn(id);
        when(session.get(Client.class, id)).thenReturn(client);

        repository.create(client);

        verify(sessionFactory, times(2)).getCurrentSession();
        verify(session, times(1)).get(Client.class, id);
        verify(session, times(1)).save(client);
    }

    @Test
    public void update() {
        Client client = mock(Client.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        repository.update(client);

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).update(client);
    }

    @Test
    public void delete() {
        Client client = mock(Client.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        repository.delete(client);

        verify(sessionFactory, times(1)).getCurrentSession();
        verify(session, times(1)).delete(client);
    }
}
