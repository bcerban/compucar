package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.Mechanic;
import com.cerbansouto.compucar.services.dataAccess.MechanicRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MechanicRepositoryImpl implements MechanicRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Mechanic> findAll() {
        return this.currentSession().createQuery("FROM Mechanic c WHERE c.deleted = 0").list();
    }

    @Override
    public Mechanic getById(long id) {
        return this.currentSession().get(Mechanic.class, id);
    }

    @Override
    public Mechanic create(Mechanic mechanic) {
        long id = (long) this.currentSession().save(mechanic);
        return this.getById(id);
    }

    @Override
    public Mechanic update(Mechanic mechanic) {
        currentSession().update(mechanic);
        return getById(mechanic.getNumber());
    }

    @Override
    public void delete(Mechanic mechanic) {
        currentSession().delete(mechanic);
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
}
