package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.MechanicService;
import com.cerbansouto.compucar.model.Mechanic;
import com.cerbansouto.compucar.services.dataAccess.MechanicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class MechanicServiceImpl implements MechanicService {

    @Autowired
    private MechanicRepository repository;

    @Cacheable("mechanics")
    @Transactional
    @Override
    public List<Mechanic> list() {
        return this.repository.findAll();
    }

    @Cacheable(value = "mechanic")
    @Transactional
    @Override
    public Mechanic fetch(Long id) {
        Mechanic found = repository.getById(id);
        if (found == null || found.isDeleted()) {
            throw new EntityNotFoundException(String.format("No mechanic with ID %d", id));
        }

        return found;
    }

    @CacheEvict(value = "mechanics", allEntries = true)
    @Transactional
    @Override
    public Mechanic create(Mechanic mechanic) throws InvalidEntityException {
        log.info("###### Creating mechanic ######");
        validateMechanic(mechanic);

        try {
            return repository.create(mechanic);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not create mechanic. Please check all information is correct.", e);
        }
    }

    @CacheEvict(value = "mechanics", allEntries = true)
    @Transactional
    @Override
    public Mechanic update(Mechanic mechanic) throws InvalidEntityException {
        log.info(String.format("###### Updating mechanic with ID %d ######", mechanic.getId()));
        validateMechanic(mechanic);

        try {
            Mechanic mechanicToUpdate = repository.getById(mechanic.getId());
            mechanicToUpdate.setName(mechanic.getName());
            mechanicToUpdate.setPhoneNumber(mechanic.getPhoneNumber());
            mechanicToUpdate.setHiringDate(mechanic.getHiringDate());
            return repository.update(mechanicToUpdate);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not update mechanic. Please check all information is correct.", e);
        }
    }

    @CacheEvict(value = "mechanics", allEntries = true)
    @Transactional
    @Override
    public void delete(Long id) {
        Mechanic mechanic = repository.getById(id);
        if (mechanic != null) {
            mechanic.setDeleted(true);
            repository.update(mechanic);
        }
    }

    private void validateMechanic(Mechanic mechanic) throws InvalidEntityException {
        validateMechanicNumber(mechanic.getNumber());
    }

    private void validateMechanicNumber(Long number) throws InvalidEntityException {
        if (number == null || number == 0) {
            throw new InvalidEntityException("Mechanic number cannot be empty.");
        }
    }
}
