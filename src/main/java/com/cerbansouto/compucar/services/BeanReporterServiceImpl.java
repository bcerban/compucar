package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.BeanReporterService;
import com.cerbansouto.compucar.model.BeanInitializationReport;
import com.cerbansouto.compucar.model.BeanInitializationTrace;
import com.cerbansouto.compucar.services.dataAccess.BeanInitializationReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
public class BeanReporterServiceImpl implements BeanReporterService, BeanPostProcessor {

    @Autowired
    private BeanInitializationReportRepository repository;

    private BeanInitializationReport beanInitializationReport;

    public BeanReporterServiceImpl() {
        this.beanInitializationReport = new BeanInitializationReport();
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        BeanInitializationTrace beanInitializationTrace = new BeanInitializationTrace();
        beanInitializationTrace.setBeanName(beanName);
        beanInitializationTrace.setBeanClassName(bean.getClass().getName());
        this.beanInitializationReport.addTrace(beanInitializationTrace);

        return bean;
    }

    @Transactional
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        repository.create(this.beanInitializationReport);
    }

    @Transactional
    @Override
    public List<BeanInitializationReport> findAll() {
        return repository.findAll();
    }
}
