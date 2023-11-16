package sujin.dev.mem.infra.repo;

import java.util.List;

public interface DataRepository<T> {
    T findById(long id);
    List<T> findAll();

    void deleteById(long id);

    void update(T t);

    void insert(T t);

}
