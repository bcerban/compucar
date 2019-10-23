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
    public List<Mechanic> getAll() {
        return this.currentSession().createQuery("from Mechanic").list();
    }

    @Override
    public Mechanic get(long id) {
        return this.currentSession().get(Mechanic.class, id);
    }

    @Override
    public Mechanic create(Mechanic mechanic) {
        long id = (long) this.currentSession().save(mechanic);
        return this.get(id);
    }

    @Override
    public Mechanic update(long id, Mechanic mechanic) {
        this.currentSession().update(mechanic);
        return this.get(id);
    }

    @Override
    public void delete(Mechanic mechanic) {
        this.currentSession().delete(mechanic);
    }

    private Session currentSession() {
        return this.sessionFactory.getCurrentSession();
    }
}
