package com.cerbansouto.compucar.services;

import com.cerbansouto.compucar.api.ReaderService;
import com.cerbansouto.compucar.model.Reader;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    public List<Reader> list() {
        throw new NotImplementedException();
    }

    @Override
    public Reader fetch(Long id) {
        return null;
    }

    public List<Reader> list(int delta) {
        throw new NotImplementedException();
    }

    public void recharge(Reader reader) {
        reader.setBatteryUsed(0);
    }

    public Reader create(Reader model) {
        throw new NotImplementedException();
    }

    public Reader update(Reader model) {
        throw new NotImplementedException();
    }

    public void delete(Reader model) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(Long id) {

    }
}
