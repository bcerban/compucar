package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.ProfileService;
import com.cerbansouto.compucar.api.TraceService;
import com.cerbansouto.compucar.model.Profile;
import com.cerbansouto.compucar.model.ProfileReport;
import com.cerbansouto.compucar.model.Trace;
import com.cerbansouto.compucar.services.dataAccess.ProfileRepository;
import com.cerbansouto.compucar.services.dataAccess.TraceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository repository;

    @Transactional
    @Override
    public List<Profile> list() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public List<Profile> list(Date from, Date to, String sort) {
        return repository.findAll(from, to, sort);
    }

    @Transactional
    @Override
    public Profile fetch(Long id) {
        Profile found = repository.getById(id);
        if (found == null) {
            throw new EntityNotFoundException(String.format("No profile with ID %d", id));
        }

        return found;
    }

    @Transactional
    @Override
    public Profile fetch(Date date, String operation) {
        return repository.getByDateAndOperation(date, operation);
    }

    @Transactional
    @Override
    public Profile create(Profile profile) {
        log.info("###### Creating profile ######");
        return repository.create(profile);
    }

    @Transactional
    @Override
    public Profile update(Profile profile) {
        log.info(String.format("###### Updating profile with ID %d ######", profile.getId()));
        return repository.update(profile);
    }

    @Transactional
    @Override
    public void delete(Profile model) {
        repository.delete(model);
    }

    @Transactional
    @Override
    public ProfileReport generate(Date date) {
        ProfileReport report = new ProfileReport();
        report.setFastestService(repository.getFastest(date));
        report.setSlowestService(repository.getSlowest(date));
        report.setMostUsed(repository.getMostUsed(date));
        report.setLeastUsed(repository.getLeastUsed(date));
        report.setExecutionTimes(repository.findAll(date));
        return report;
    }
}
