package bootcamp.bankApi.service;

import java.util.List;

public interface Service<T> {

    T findById(int id);
    void save(T model);
    void update(T model);
    void delete(T model);
    List<T> findAll();

}
