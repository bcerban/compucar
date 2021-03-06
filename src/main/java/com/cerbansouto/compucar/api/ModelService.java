package com.cerbansouto.compucar.api;

import com.cerbansouto.compucar.services.InvalidEntityException;

import java.util.List;

public interface ModelService<T> {
    List<T> list();
    T create(T model) throws InvalidEntityException;
    T update(T model) throws InvalidEntityException;
    void delete(T model);
}
