package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.Service;
import com.cerbansouto.compucar.services.InvalidEntityException;

public interface ServiceService {
    Service create(Service service) throws InvalidEntityException;
}
