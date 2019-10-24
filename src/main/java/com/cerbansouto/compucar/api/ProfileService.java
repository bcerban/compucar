package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.Profile;
import com.cerbansouto.compucar.model.ProfileReport;

import java.util.Date;
import java.util.List;

public interface ProfileService extends ModelService<Profile> {
    List<Profile> list(Date from, Date to, String sort);
    Profile fetch(Long id);
    Profile fetch(Date date, String operation);
    ProfileReport generate(Date date);
}
