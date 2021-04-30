package com.gmail.alexandr.tsiulkin.repository;

import java.util.List;

public interface GenericRepository<I, T> {

    void persist(T entity);

    T findById(I id);

    List<T> findAll();

    void removeById(I id);

    void merge(T entity);

    void clear();
}
