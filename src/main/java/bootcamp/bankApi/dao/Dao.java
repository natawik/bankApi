package bootcamp.bankApi.dao;

import java.util.List;

public interface Dao<T> {

    T findById(int id);
    void save(T model);
    void update(T model);
    void delete(T model);
    List<T> findAll();

}
