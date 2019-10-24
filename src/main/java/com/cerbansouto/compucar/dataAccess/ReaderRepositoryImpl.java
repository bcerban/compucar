package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Reader;
import com.cerbansouto.compucar.services.dataAccess.ReaderRepository;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
    public List<Reader> findAll(int delta) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "FROM Reader r WHERE r.deleted = 0 AND (r.batteryLife - r.batteryUsed) <= :delta"
        );
        query.setParameter("delta", delta);
        return query.list();
    }

    @Override
    public Reader getByCode(String code) {
        return sessionFactory.getCurrentSession().get(Reader.class, code);
    }

    @Override
    public Reader create(Reader entity) {
        String savedId = (String)sessionFactory.getCurrentSession().save(entity);
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
