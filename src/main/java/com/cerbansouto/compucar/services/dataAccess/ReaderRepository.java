package com.cerbansouto.compucar.services.dataAccess;

import com.cerbansouto.compucar.model.Reader;

import java.util.List;

public interface ReaderRepository extends Repository<Reader> {
    List<Reader> findAll(int delta);
}
