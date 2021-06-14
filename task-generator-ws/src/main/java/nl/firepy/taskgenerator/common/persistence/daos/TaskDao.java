package nl.firepy.taskgenerator.common.persistence.daos;

import nl.firepy.taskgenerator.tasks.Task;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Stateless
public class TaskDao implements BaseDao<Task> {

    @PersistenceContext
    EntityManager em;

    public List<Task> findByName(String name) {
        TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t WHERE t.name = :name", Task.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public Optional<Task> get(int id) {
        return Optional.ofNullable(em.find(Task.class, id));
    }

    @Override
    public void save(Task task) {
        em.persist(task);
    }

    @Override
    public void delete(int id) {
        get(id).ifPresentOrElse(em::remove,
                () -> { throw new NotFoundException(); });
    }
}
