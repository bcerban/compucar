package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.EventDiagnosis;
import com.cerbansouto.compucar.services.dataAccess.DiagnosisRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiagnosisRepositoryImpl implements DiagnosisRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<EventDiagnosis> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM EventDiagnosis e").list();
    }

    @Override
    public EventDiagnosis create(EventDiagnosis entity) {
        Long savedId = (Long)sessionFactory.getCurrentSession().save(entity);
        return sessionFactory.getCurrentSession().get(EventDiagnosis.class, savedId);
    }

    @Override
    public EventDiagnosis update(EventDiagnosis entity) {
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    @Override
    public void delete(EventDiagnosis entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }
}
