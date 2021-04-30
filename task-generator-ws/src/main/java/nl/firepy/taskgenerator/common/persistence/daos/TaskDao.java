package nl.firepy.taskgenerator.common.persistence.daos;

import nl.firepy.taskgenerator.tasks.Task;

import javax.persistence.TypedQuery;
import java.util.List;

public class TaskDao extends BaseDao<Task> {

    public List<Task> findByName(String name) {
        TypedQuery<Task> query = em().createQuery("SELECT t FROM Task t WHERE t.name = :name", Task.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}
