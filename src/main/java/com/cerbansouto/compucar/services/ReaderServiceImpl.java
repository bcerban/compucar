package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.ReaderService;
import com.cerbansouto.compucar.api.WorkshopService;
import com.cerbansouto.compucar.model.Reader;
import com.cerbansouto.compucar.model.Workshop;
import com.cerbansouto.compucar.services.dataAccess.ReaderRepository;
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
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderRepository repository;

    @Autowired
    private WorkshopService workshopService;

    @Cacheable("readers")
    @Override
    @Transactional
    public List<Reader> list() {
        log.info("###### Listing readers ######");
        return repository.findAll();
    }

    @Cacheable(value = "reader")
    @Override
    @Transactional
    public Reader fetch(String code) {
        log.info(String.format("###### Fetching reader with code %s ######", code));
        Reader found = repository.getByCode(code);

        if (found == null || found.isDeleted()) {
            throw new EntityNotFoundException(String.format("No reader with code %s", code));
        }

        return found;
    }

    @Cacheable(value = "readers")
    @Override
    @Transactional
    public List<Reader> list(int delta) {
        log.info("###### Listing readers bu delta ######");
        return repository.findAll(delta);
    }

    @CacheEvict(value = "readers", allEntries = true)
    @Override
    @Transactional
    public void recharge(Reader reader) {
        reader.setBatteryUsed(0);
        repository.update(reader);
    }

    @CacheEvict(value = "readers", allEntries = true)
    @Override
    @Transactional
    public Reader create(Reader model) throws InvalidEntityException {
        log.info(String.format("###### Creating reader with code %s ######", model.getCode()));
        validateReader(model);

        try {
            model.setWorkshop(workshopService.fetch(model.getWorkshop().getCode()));
            return repository.create(model);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not create reader. Please check all information is correct.", e);
        } catch (EntityNotFoundException e) {
            throw new InvalidEntityException("Workshop not found.", e);
        }
    }

    @CacheEvict(value = "readers", allEntries = true)
    @Override
    @Transactional
    public Reader update(Reader model) throws InvalidEntityException {
        log.info(String.format("###### Updating reader with code %d ######", model.getCode()));
        validateReader(model);

        try {
            Reader reader = fetch(model.getCode());
            reader.setBrand(model.getBrand());
            reader.setWorkshop(workshopService.fetch(model.getWorkshop().getCode()));
            return repository.update(reader);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidEntityException("Could not update reader. Please check all information is correct.", e);
        }
    }

    @CacheEvict(value = "readers", allEntries = true)
    @Override
    @Transactional
    public void delete(Reader reader) {
        reader.setDeleted(true);
        repository.update(reader);
    }

    private void validateReader(Reader reader) throws InvalidEntityException {
        validateReaderCode(reader.getCode());
        validateWorkshop(reader.getWorkshop());
    }

    private void validateReaderCode(String code) throws InvalidEntityException {
        if (code == null || code.isEmpty()) {
            throw new InvalidEntityException("Reader code cannot be empty.");
        }
    }

    private void validateWorkshop(Workshop workshop) throws InvalidEntityException {
        if (workshop == null || workshop.getCode() == null || workshop.getCode().isEmpty()) {
            throw new InvalidEntityException("Workshop code cannot be empty.");
        }
    }
}
