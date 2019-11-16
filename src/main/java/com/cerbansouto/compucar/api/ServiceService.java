package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.EventDiagnosis;
import com.cerbansouto.compucar.model.Service;
import com.cerbansouto.compucar.services.EventService;
import com.cerbansouto.compucar.services.InvalidEntityException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface ServiceService {
    Service fetch(String code) throws EntityNotFoundException;
    Service create(Service service) throws InvalidEntityException;
    List<Service> getForMonth(long month);
}
