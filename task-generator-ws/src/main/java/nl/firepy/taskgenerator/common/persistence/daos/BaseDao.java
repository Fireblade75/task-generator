package nl.firepy.taskgenerator.common.persistence.daos;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseDao<E> {

    private static final String persistenceUnitName =
            System.getenv("PERSISTENCE_UNIT") != null ? System.getenv("PERSISTENCE_UNIT") : "office";

    protected static final EntityManager em =
            Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();

    public E get(int id) {
        E e = em.find(E(), id);
        em.detach(e);
        return e;
    }

    public List<E> getAll() {
        return em.createQuery("SELECT e FROM " + E().getSimpleName() + " e", E()).getResultList();
    }

    protected List<E> detachList(List<E> elements) {
        elements.forEach(em::detach);
        return elements;
    }

    public void save(E e) {
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
    }

    public void update(E e) {
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
    }

    private Class<E> E() {
        ParameterizedType thisDaoClass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) thisDaoClass.getActualTypeArguments()[0];
    }
}
