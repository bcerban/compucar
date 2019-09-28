package com.cerbansouto.compucar.api;

import java.util.List;

public interface ModelService<T> {
    List<T> list();
    T fetch(String code);
    T create(T model);
    T update(T model);
    void delete(T model);
}
