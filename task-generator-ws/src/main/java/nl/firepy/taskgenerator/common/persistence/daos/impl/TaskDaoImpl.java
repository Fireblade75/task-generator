package nl.firepy.taskgenerator.common.persistence.daos.impl;

import lombok.extern.log4j.Log4j2;
import nl.firepy.taskgenerator.common.persistence.daos.TaskDao;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;
import nl.firepy.taskgenerator.common.persistence.entities.TaskEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Log4j2
@Stateless
@TransactionManagement
public class TaskDaoImpl implements TaskDao {

    @PersistenceContext
    EntityManager em;

    @Override public List<TaskEntity> findByName(String name) {
        TypedQuery<TaskEntity> query = em.createQuery("SELECT t FROM Task t WHERE t.name = :name", TaskEntity.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override public List<TaskEntity> findByProject(ProjectEntity project) {
        var query = em.createNamedQuery("Task.findByProject", TaskEntity.class);
        query.setParameter("project", project);
        return query.getResultList();
    }

    @Override
    public Optional<TaskEntity> get(int id) {
        return Optional.ofNullable(em.find(TaskEntity.class, id));
    }

    @Override
    public void save(TaskEntity task) {
        em.persist(task);
    }

    @Override
    public void delete(int id) {
        get(id).ifPresentOrElse(em::remove,
                () -> { throw new NotFoundException(); });
    }
}
