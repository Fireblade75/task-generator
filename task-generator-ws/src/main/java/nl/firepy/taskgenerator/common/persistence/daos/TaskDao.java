package nl.firepy.taskgenerator.common.persistence.daos;

public class TaskDao extends BaseDao<TaskDao> {

    public void findByName() {
        em.createQuery("SELECT t FROM Task t WHERE t.name = :name");
    }
}
