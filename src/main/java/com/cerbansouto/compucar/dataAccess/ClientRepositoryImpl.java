package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Client;
import com.cerbansouto.compucar.services.dataAccess.ClientRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ClientRepositoryImpl implements ClientRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Client> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Client c WHERE c.deleted = 0").list();
    }

    @Override
    public Client getById(long id) {
        return sessionFactory.getCurrentSession().get(Client.class, id);
    }

    @Override
    public Client create(Client client) {
        long savedId = (long)sessionFactory.getCurrentSession().save(client);
        return sessionFactory.getCurrentSession().get(Client.class, savedId);
    }

    @Override
    public Client update(Client client) {
        sessionFactory.getCurrentSession().update(client);
        return client;
    }

    @Override
    public void delete(Client client) {
        sessionFactory.getCurrentSession().delete(client);
    }
}
