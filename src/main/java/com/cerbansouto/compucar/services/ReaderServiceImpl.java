package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.ReaderService;
import com.cerbansouto.compucar.api.WorkshopService;
import com.cerbansouto.compucar.model.Reader;
import com.cerbansouto.compucar.model.Workshop;
import com.cerbansouto.compucar.services.dataAccess.ReaderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;

@Slf4j
@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderRepository repository;

    @Autowired
    private WorkshopService workshopService;

    @Override
    @Transactional
    public List<Reader> list() {
        log.info("###### Listing readers ######");
        return repository.findAll();
    }

    @Override
    @Transactional
    public Reader fetch(Long id) {
        log.info(String.format("###### Fetching reader with ID %d ######", id));
        Reader found = repository.getById(id);

        if (found == null || found.isDeleted()) {
            throw new EntityNotFoundException(String.format("No reader with ID %d", id));
        }

        return found;
    }

    public List<Reader> list(int delta) {
        throw new NotImplementedException();
    }

    public void recharge(Reader reader) {
        reader.setBatteryUsed(0);
    }

    @Override
    @Transactional
    public Reader create(Reader model) throws InvalidEntityException {
        log.info(String.format("###### Creating reader with code %s ######", model.getCode()));
        validateReader(model);

        try {
            model.setWorkshop(workshopService.fetch(model.getWorkshopCode()));
            return repository.create(model);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not create reader. Please check all information is correct.", e);
        } catch (EntityNotFoundException e) {
            throw new InvalidEntityException("Workshop not found.", e);
        }
    }

    @Override
    @Transactional
    public Reader update(Reader model) throws InvalidEntityException {
        log.info(String.format("###### Updating reader with ID %d ######", model.getId()));
        validateReader(model);

        try {
            Reader reader = fetch(model.getId());
            reader.setBrand(model.getBrand());
            reader.setWorkshop(workshopService.fetch(model.getWorkshopCode()));
            return repository.update(reader);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not update reader. Please check all information is correct.", e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Reader existingModel = repository.getById(id);
        if (existingModel != null) {
            existingModel.setDeleted(true);
            repository.update(existingModel);
        }
    }

    private void validateReader(Reader reader) throws InvalidEntityException {
        validateReaderCode(reader.getCode());
        validateWorkshopCode(reader.getWorkshopCode());
    }

    private void validateReaderCode(String code) throws InvalidEntityException {
        if (code == null || code.isEmpty()) {
            throw new InvalidEntityException("Reader code cannot be empty.");
        }
    }

    private void validateWorkshopCode(String code) throws InvalidEntityException {
        if (code == null || code.isEmpty()) {
            throw new InvalidEntityException("Workshop code cannot be empty.");
        }
    }
}
