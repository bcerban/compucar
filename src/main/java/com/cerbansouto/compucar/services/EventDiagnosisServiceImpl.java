package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.EventDiagnosisService;
import com.cerbansouto.compucar.model.EventDiagnosis;
import com.cerbansouto.compucar.services.dataAccess.DiagnosisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class EventDiagnosisServiceImpl implements EventDiagnosisService {

    @Autowired
    private DiagnosisRepository repository;

    @Autowired
    private EventService eventService;

    @Override
    public List<EventDiagnosis> list() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public EventDiagnosis create(EventDiagnosis model) throws InvalidEntityException {
        List<String> names = eventService.fetchEventNames(model.getService().getCode());
        if (!names.contains(model.getEventName())) {
            throw new InvalidEntityException(String.format("Event %s is not registered for service %s", model.getEventName(), model.getService().getCode()));
        }
        return repository.create(model);
    }

    @Transactional
    @Override
    public EventDiagnosis update(EventDiagnosis model) throws InvalidEntityException {
        // TODO: validate
        return repository.update(model);
    }

    @Transactional
    @Override
    public void delete(EventDiagnosis model) {
        repository.delete(model);
    }
}
