package com.cerbansouto.compucar.services.dataAccess;

import com.cerbansouto.compucar.model.Mechanic;

import java.util.List;

public interface MechanicRepository extends Repository<Mechanic> {
    Mechanic getById(long id);
}
