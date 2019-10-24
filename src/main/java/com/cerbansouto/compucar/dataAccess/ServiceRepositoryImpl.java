package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Service;
import com.cerbansouto.compucar.services.dataAccess.ServiceRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceRepositoryImpl implements ServiceRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Service> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Service s WHERE s.deleted = 0").list();
    }

    @Override
    public Service getByCode(String code) {
        return sessionFactory.getCurrentSession().get(Service.class, code);
    }

    @Override
    public Service create(Service entity) {
        String savedId = (String)sessionFactory.getCurrentSession().save(entity);
        return sessionFactory.getCurrentSession().get(Service.class, savedId);
    }

    @Override
    public Service update(Service entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    @Override
    public void delete(Service entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }
}
