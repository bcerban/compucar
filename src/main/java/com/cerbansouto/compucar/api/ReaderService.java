package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.model.Reader;

import java.util.List;

public interface ReaderService extends ModelService<Reader> {
    List<Reader> list(int delta);
    Reader fetch(String code);
    void recharge(Reader reader);
}
