package nl.firepy.taskgenerator.common.persistence.daos;

import java.util.Optional;

public interface BaseDao<E> {

    Optional<E> get(int id);

    void save(E e);


    void delete(int id);
}
