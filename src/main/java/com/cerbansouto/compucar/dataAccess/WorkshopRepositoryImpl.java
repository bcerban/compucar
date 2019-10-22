package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Workshop;
import com.cerbansouto.compucar.services.dataAccess.WorkshopRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class WorkshopRepositoryImpl implements WorkshopRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Workshop> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Workshop w WHERE w.deleted = 0").list();
    }

    @Override
    public Workshop getById(long id) {
        return sessionFactory.getCurrentSession().get(Workshop.class, id);
    }

    @Override
    public Workshop create(Workshop entity) {
        long savedId = (long)sessionFactory.getCurrentSession().save(entity);
        return sessionFactory.getCurrentSession().get(Workshop.class, savedId);
    }

    @Override
    public Workshop update(Workshop entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    @Override
    public void delete(Workshop entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }
}
