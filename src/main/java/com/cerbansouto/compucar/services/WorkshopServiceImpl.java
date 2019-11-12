package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.WorkshopService;
import com.cerbansouto.compucar.model.Workshop;
import com.cerbansouto.compucar.services.dataAccess.WorkshopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
public class WorkshopServiceImpl implements WorkshopService {
    @Autowired
    private WorkshopRepository repository;

    @Cacheable("workshops")
    @Transactional
    @Override
    public List<Workshop> list() {
        log.info("###### Listing workshops ######");
        return repository.findAll();
    }

    @Cacheable("workshop")
    @Override
    @Transactional
    public Workshop fetch(String code) {
        log.info(String.format("###### Fetching workshop with code %s ######", code));
        Workshop found = repository.getByCode(code);

        if (found == null || found.isDeleted()) {
            throw new EntityNotFoundException(String.format("No workshop with code %s", code));
        }

        return found;
    }

    @CacheEvict(value = "workshops", allEntries = true)
    @Transactional
    @Override
    public Workshop create(Workshop model) throws InvalidEntityException {
        log.info(String.format("###### Creating workshop with code %s ######", model.getCode()));
        validateWorkshopCode(model);

        try {
            return repository.create(model);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not create workshop. Please check all information is correct.", e);
        }
    }

    @CacheEvict(value = "workshops", allEntries = true)
    @Transactional
    @Override
    public Workshop update(Workshop model) throws InvalidEntityException {
        log.info(String.format("###### Updating workshop with code %s ######", model.getCode()));
        validateWorkshopCode(model);

        try {
            Workshop existingWorkshop = fetch(model.getCode());
            existingWorkshop.setName(model.getName());
            existingWorkshop.setAddress(model.getAddress());
            existingWorkshop.setCity(model.getCity());
            return repository.update(existingWorkshop);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not create workshop. Please check all information is correct.", e);
        }
    }

    @CacheEvict(value = "workshops", allEntries = true)
    @Transactional
    @Override
    public void delete(Workshop model) {
        model.setDeleted(true);
        repository.update(model);
    }

    private void validateWorkshopCode(Workshop workshop) throws InvalidEntityException {
        if (workshop.getCode() == null || workshop.getCode().isEmpty()) {
            throw new InvalidEntityException("Workshop code cannot be empty.");
        }
    }
}
