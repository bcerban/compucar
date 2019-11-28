package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.BeanInitializationReport;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

public interface BeanReporterService {
    List<BeanInitializationReport> findAll();
    void handleContextRefresh(ContextRefreshedEvent event);
}
