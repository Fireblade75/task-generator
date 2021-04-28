package nl.firepy.taskgenerator.common.persistence.daos;

import nl.firepy.taskgenerator.tasks.Task;

public class TaskDao extends BaseDao<Task> {

    public void findByName() {
        em.createQuery("SELECT t FROM Task t WHERE t.name = :name");
    }
}
