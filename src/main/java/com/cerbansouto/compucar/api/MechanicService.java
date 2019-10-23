package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.Mechanic;

import java.util.List;

public interface MechanicService {
    List<Mechanic> getAll();
    Mechanic get(long id);
    Mechanic create(Mechanic mechanic);
    Mechanic update(long id, Mechanic mechanic);
    void delete(long id);
}
