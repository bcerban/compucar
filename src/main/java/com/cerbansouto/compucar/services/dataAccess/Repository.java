package com.cerbansouto.compucar.services.dataAccess;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();
    T getById(long id);
    T create(T entity);
    T update(T entity);
    void delete(T entity);
}
