package com.cerbansouto.compucar.services.dataAccess;

import com.cerbansouto.compucar.model.Service;

public interface ServiceRepository extends Repository<Service> {
    Service getByCode(String code);
}
