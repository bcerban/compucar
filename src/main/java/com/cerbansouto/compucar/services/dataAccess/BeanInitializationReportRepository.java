package com.cerbansouto.compucar.services.dataAccess;

import com.cerbansouto.compucar.model.BeanInitializationReport;

import java.util.List;

public interface BeanInitializationReportRepository {
    List<BeanInitializationReport> findAll();
    void create(BeanInitializationReport beanInitializationReport);
}
