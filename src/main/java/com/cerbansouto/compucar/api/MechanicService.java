package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.Mechanic;

public interface MechanicService extends ModelService<Mechanic> {
    Mechanic fetch(Long id);
}
