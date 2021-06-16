package nl.firepy.taskgenerator.common.persistence.daos;

import nl.firepy.taskgenerator.common.persistence.entities.AccountEntity;
import nl.firepy.taskgenerator.common.persistence.entities.CategoryEntity;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;
import nl.firepy.taskgenerator.tasks.Task;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

public class CategoriesDao implements BaseDao<CategoryEntity> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<CategoryEntity> get(int id) {
        return Optional.ofNullable(em.find(CategoryEntity.class, id));
    }

    public List<CategoryEntity> findByProject(ProjectEntity project) {
        var query = em.createNamedQuery("Category.findByProject", CategoryEntity.class);
        query.setParameter("project", project);
        return query.getResultList();
    }

    @Override
    public void save(CategoryEntity category) {
        em.persist(category);
    }

    @Override
    public void delete(int id) {
        get(id).ifPresentOrElse(em::remove,
                () -> { throw new NotFoundException(); });
    }
}
