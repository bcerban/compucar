package com.cerbansouto.compucar.services.dataAccess;

import com.cerbansouto.compucar.model.Mechanic;

import java.util.List;

public interface MechanicRepository {
    List<Mechanic> getAll();
    Mechanic get(long id);
    Mechanic create(Mechanic mechanic);
    Mechanic update(long id, Mechanic mechanic);
    void delete(Mechanic mechanic);
}
