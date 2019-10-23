package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Reader;
import com.cerbansouto.compucar.services.dataAccess.ReaderRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReaderRepositoryImpl implements ReaderRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Reader> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Reader r WHERE r.deleted = 0").list();
    }

    @Override
    public Reader getById(long id) {
        return sessionFactory.getCurrentSession().get(Reader.class, id);
    }

    @Override
    public Reader create(Reader entity) {
        long savedId = (long)sessionFactory.getCurrentSession().save(entity);
        return sessionFactory.getCurrentSession().get(Reader.class, savedId);
    }

    @Override
    public Reader update(Reader entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    @Override
    public void delete(Reader entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }
}
