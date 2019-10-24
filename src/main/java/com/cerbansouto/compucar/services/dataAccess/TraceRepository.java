package com.cerbansouto.compucar.services.dataAccess;

import com.cerbansouto.compucar.model.Trace;

import java.util.Date;
import java.util.List;

public interface TraceRepository extends Repository<Trace> {
    List<Trace> findAll(Date from, Date to, String sort);
    Trace getById(Long id);
}
