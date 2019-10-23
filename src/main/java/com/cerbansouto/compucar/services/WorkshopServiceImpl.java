package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.WorkshopService;
import com.cerbansouto.compucar.model.Workshop;
import com.cerbansouto.compucar.services.dataAccess.WorkshopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    @Override
    public List<Workshop> list() {
        log.info("###### Listing workshops ######");
        return repository.findAll();
    }

    @Transactional
    @Override
    public Workshop fetch(Long id) {
        log.info(String.format("###### Fetching workshop with ID %d ######", id));
        Workshop found = repository.getById(id);

        if (found == null || found.isDeleted()) {
            throw new EntityNotFoundException(String.format("No workshop with ID %d", id));
        }

        return found;
    }

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

    @Transactional
    @Override
    public Workshop update(Workshop model) throws InvalidEntityException {
        log.info(String.format("###### Updating workshop with ID %d ######", model.getId()));
        validateWorkshopCode(model);

        try {
            Workshop existingWorkshop = fetch(model.getId());
            existingWorkshop.setName(model.getName());
            existingWorkshop.setAddress(model.getAddress());
            existingWorkshop.setCity(model.getCity());
            return repository.update(existingWorkshop);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not create workshop. Please check all information is correct.", e);
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Workshop existingModel = repository.getById(id);
        if (existingModel != null) {
            existingModel.setDeleted(true);
            repository.update(existingModel);
        }
    }

    private void validateWorkshopCode(Workshop workshop) throws InvalidEntityException {
        if (workshop.getCode() == null || workshop.getCode().isEmpty()) {
            throw new InvalidEntityException("Workshop code cannot be empty.");
        }
    }
}
