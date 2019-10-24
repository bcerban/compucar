package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Trace;
import com.cerbansouto.compucar.services.dataAccess.TraceRepository;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TraceRepositoryImpl implements TraceRepository {

    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Trace> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Trace t").list();
    }

    @Override
    public List<Trace> findAll(Date from, Date to, String sort) {
        if (sort == null || !sort.toUpperCase().equals(ASC)) {
            sort = DESC;
        }

        String queryString = "FROM Trace t";
        if (from != null && to != null) {
            queryString += " WHERE DATE(t.date) >= :from_date AND DATE(t.date) <= :to_date ORDER BY t.date " + sort;
        } else if (from != null) {
            queryString += " WHERE DATE(t.date) >= :from_date ORDER BY t.date " + sort;
        } else if (to != null) {
            queryString += " WHERE DATE(t.date) <= :to_date ORDER BY t.date " + sort;
        }

        Query query = sessionFactory.getCurrentSession().createQuery(queryString);

        if (from != null) {
            query.setParameter("from_date", from);
        }

        if (to != null) {
            query.setParameter("to_date", to);
        }

        return query.list();
    }

    @Override
    public Trace getById(Long id) {
        return sessionFactory.getCurrentSession().get(Trace.class, id);
    }

    @Override
    public Trace create(Trace trace) {
        long id = (long)sessionFactory.getCurrentSession().save(trace);
        return this.getById(id);
    }

    @Override
    public Trace update(Trace trace) {
        sessionFactory.getCurrentSession().update(trace);
        return getById(trace.getId());
    }

    @Override
    public void delete(Trace trace) {
        sessionFactory.getCurrentSession().delete(trace);
    }
}
