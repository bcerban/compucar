package com.cerbansouto.compucar.services.dataAccess;

import com.cerbansouto.compucar.model.Profile;

import java.util.Date;
import java.util.List;

public interface ProfileRepository extends Repository<Profile> {
    List<Profile> findAll(Date from, Date to, String sort);
    List<Profile> findAll(Date date);
    Profile getById(Long id);
    Profile getByDateAndOperation(Date date, String operation);
    Profile getLeastUsed(Date date);
    Profile getMostUsed(Date date);
    Profile getFastest(Date date);
    Profile getSlowest(Date date);
}
