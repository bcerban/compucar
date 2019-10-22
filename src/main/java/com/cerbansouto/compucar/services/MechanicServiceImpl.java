package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.MechanicService;
import com.cerbansouto.compucar.model.Mechanic;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class MechanicServiceImpl implements MechanicService {
    public List<Mechanic> list() {
        throw new NotImplementedException();
    }

    @Override
    public Mechanic fetch(Long id) {
        return null;
    }

    public Mechanic create(Mechanic model) {
        throw new NotImplementedException();
    }

    public Mechanic update(Mechanic model) {
        throw new NotImplementedException();
    }

    public void delete(Mechanic model) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(Long id) {

    }
}
