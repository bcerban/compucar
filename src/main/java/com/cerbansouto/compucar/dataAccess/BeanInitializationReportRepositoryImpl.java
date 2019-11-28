package com.cerbansouto.compucar.dataAccess;

import com.cerbansouto.compucar.model.BeanInitializationReport;
import com.cerbansouto.compucar.services.dataAccess.BeanInitializationReportRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BeanInitializationReportRepositoryImpl implements BeanInitializationReportRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<BeanInitializationReport> findAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM BeanInitializationReport b").list();
    }

    @Override
    public void create(BeanInitializationReport beanInitializationReport) {
        sessionFactory.getCurrentSession().save(beanInitializationReport);
    }
}
