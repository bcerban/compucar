package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Profile;
import com.cerbansouto.compucar.model.Trace;
import com.cerbansouto.compucar.services.dataAccess.ProfileRepository;
import com.cerbansouto.compucar.services.dataAccess.TraceRepository;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

@Component
public class ProfileRepositoryImpl implements ProfileRepository {

    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Profile> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Profile p").list();
    }

    @Override
    public List<Profile> findAll(Date date) {
        String queryString = "FROM Profile p";
        if (date != null) {
            queryString += " WHERE DATE(p.date) = :date";
        }

        Query query = sessionFactory.getCurrentSession().createQuery(queryString);

        if (date != null) {
            query.setParameter("date", date);
        }

        return query.list();
    }

    @Override
    public List<Profile> findAll(Date from, Date to, String sort) {
        if (sort == null || !sort.toUpperCase().equals(ASC)) {
            sort = DESC;
        }

        String queryString = "FROM Profile p";
        if (from != null && to != null) {
            queryString += " WHERE DATE(p.date) >= :from_date AND DATE(p.date) <= :to_date ORDER BY p.date " + sort;
        } else if (from != null) {
            queryString += " WHERE DATE(p.date) >= :from_date ORDER BY p.date " + sort;
        } else if (to != null) {
            queryString += " WHERE DATE(p.date) <= :to_date ORDER BY p.date " + sort;
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
    public Profile getById(Long id) {
        return sessionFactory.getCurrentSession().get(Profile.class, id);
    }

    @Override
    public Profile getByDateAndOperation(Date date, String operation) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "FROM Profile p WHERE p.operation = :operation AND DATE(p.date) = :date"
        );
        query.setParameter("operation", operation);
        query.setParameter("date", date);

        try {
            return (Profile)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Profile getLeastUsed(Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "FROM Profile p WHERE DATE(p.date) = :date AND p.numInvocations = " +
                        "(SELECT MIN(p2.numInvocations) FROM Profile p2 WHERE DATE(p2.date) = :date)"
        );
        query.setParameter("date", date);

        try {
            return (Profile)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Profile getMostUsed(Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "FROM Profile p WHERE DATE(p.date) = :date AND p.numInvocations = " +
                        "(SELECT MAX(p2.numInvocations) FROM Profile p2 WHERE DATE(p2.date) = :date)"
        );
        query.setParameter("date", date);

        try {
            return (Profile)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Profile getFastest(Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "FROM Profile p WHERE DATE(p.date) = :date AND p.averageResponseTime = " +
                        "(SELECT MIN(p2.averageResponseTime) FROM Profile p2 WHERE DATE(p2.date) = :date)"
        );
        query.setParameter("date", date);

        try {
            return (Profile)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Profile getSlowest(Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "FROM Profile p WHERE DATE(p.date) = :date AND p.averageResponseTime = " +
                        "(SELECT MAX(p2.averageResponseTime) FROM Profile p2 WHERE DATE(p2.date) = :date)"
        );
        query.setParameter("date", date);

        try {
            return (Profile)query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Profile create(Profile profile) {
        long id = (long)sessionFactory.getCurrentSession().save(profile);
        return this.getById(id);
    }

    @Override
    public Profile update(Profile profile) {
        sessionFactory.getCurrentSession().update(profile);
        return getById(profile.getId());
    }

    @Override
    public void delete(Profile profile) {
        sessionFactory.getCurrentSession().delete(profile);
    }
}
