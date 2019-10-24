package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.Trace;

import java.util.Date;
import java.util.List;

public interface TraceService extends ModelService<Trace> {
    String USERNAME_HEADER = "User-Name";

    List<Trace> list(Date from, Date to, String sort);
    Trace fetch(Long id);
}
