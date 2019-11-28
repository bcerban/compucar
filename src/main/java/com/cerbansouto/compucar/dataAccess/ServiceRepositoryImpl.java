package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Client;
import com.cerbansouto.compucar.model.Mechanic;
import com.cerbansouto.compucar.model.Reader;
import com.cerbansouto.compucar.model.Service;
import com.cerbansouto.compucar.services.dataAccess.ServiceRepository;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
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
    public int getCountForClientOnDate(Client client, Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "FROM Service s WHERE s.client.number = :client_number AND DATE(s.date) = :date"
        );
        query.setParameter("client_number", client.getNumber());
        query.setParameter("date", date);
        return query.list().size();
    }

    @Override
    public int getCountForClientOnMonth(Client client, Date to) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "FROM Service s WHERE s.client.number = :client_number AND MONTH(s.date) = MONTH(:date)"
        );
        query.setParameter("client_number", client.getNumber());
        query.setParameter("date", to);
        return query.list().size();
    }

    public List<Service> listByMechanicAndDate(Mechanic mechanic, Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "FROM Service s WHERE s.mechanic.number = :mechanic_number AND DATE(s.date) = :date"
        );
        query.setParameter("mechanic_number", mechanic.getNumber());
        query.setParameter("date", date);
        return query.list();
    }

    @Override
    public List<Service> listByMonth(int month) {
        Query query = sessionFactory.getCurrentSession().createQuery(
          "FROM Service s WHERE MONTH(s.date) = :month"
        );
        query.setParameter("month", month);
        return query.list();
    }

    @Override
    public List<Service> listByDateRange(Date from, Date to) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "FROM Service s WHERE s.date >= :fromDate AND s.date <= :toDate"
        );
        query.setParameter("fromDate", from);
        query.setParameter("toDate", to);
        return query.list();
    }

    @Override
    public List<Service> listByReaderAndDateRange(Reader reader, Date from, Date to) {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "FROM Service s WHERE s.reader.code = :code AND s.date >= :fromDate AND s.date <= :toDate"
        );
        query.setParameter("code", reader.getCode());
        query.setParameter("fromDate", from);
        query.setParameter("toDate", to);
        return query.list();
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
