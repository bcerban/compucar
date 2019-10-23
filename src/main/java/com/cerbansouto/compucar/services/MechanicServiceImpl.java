package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.MechanicService;
import com.cerbansouto.compucar.model.Mechanic;
import com.cerbansouto.compucar.services.dataAccess.MechanicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MechanicServiceImpl implements MechanicService {

    @Autowired
    private MechanicRepository mechanicRepository;

    @Transactional
    @Override
    public List<Mechanic> getAll() {
        return this.mechanicRepository.getAll();
    }

    @Transactional
    @Override
    public Mechanic get(long id) {
        return this.mechanicRepository.get(id);
    }

    @Transactional
    @Override
    public Mechanic create(Mechanic mechanic) {
        return this.mechanicRepository.create(mechanic);
    }

    @Override
    public Mechanic update(long id, Mechanic mechanic) {
        Mechanic mechanicToUpdate = this.mechanicRepository.get(id);
        mechanicToUpdate.setName(mechanic.getName());
        mechanicToUpdate.setPhoneNumber(mechanic.getPhoneNumber());
        mechanicToUpdate.setHiringDate(mechanic.getHiringDate());
        return this.mechanicRepository.update(id, mechanicToUpdate);
    }

    @Override
    public void delete(long id) {
        Mechanic mechanic = this.get(id);
        this.mechanicRepository.delete(mechanic);
    }
}
