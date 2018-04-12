package ru.kai.dao;

import java.util.List;
import java.util.Optional;

/**
 * 04.04.2018
 * CrudDao
 *
 * @version v1.0
 */
public interface CrudDao<T> {
    Optional<T> find(Integer id);
    Integer save(T model);
    void update(T model);
    void delete(Integer id);

    List<T> findAll();
}
