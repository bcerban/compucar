package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.TraceService;
import com.cerbansouto.compucar.model.Trace;
import com.cerbansouto.compucar.services.dataAccess.TraceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TraceServiceImpl implements TraceService {

    @Autowired
    private TraceRepository repository;

    @Transactional
    @Override
    public List<Trace> list() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public List<Trace> list(Date from, Date to, String sort) {
        return repository.findAll(from, to, sort);
    }

    @Transactional
    @Override
    public Trace fetch(Long id) {
        Trace found = repository.getById(id);
        if (found == null) {
            throw new EntityNotFoundException(String.format("No trace with ID %d", id));
        }

        return found;
    }

    @Transactional
    @Override
    public Trace create(Trace trace) {
        log.info("###### Creating trace ######");
        return repository.create(trace);
    }

    @Transactional
    @Override
    public Trace update(Trace trace) {
        log.info(String.format("###### Updating trace with ID %d ######", trace.getId()));
        Trace mechanicToUpdate = fetch(trace.getId());
        mechanicToUpdate.setDate(trace.getDate());
        mechanicToUpdate.setOperation(trace.getOperation());
        mechanicToUpdate.setUsername(trace.getUsername());
        mechanicToUpdate.setRequestInfo(trace.getRequestInfo());
        return repository.update(mechanicToUpdate);
    }

    @Transactional
    @Override
    public void delete(Trace model) {
        repository.delete(model);
    }
}
