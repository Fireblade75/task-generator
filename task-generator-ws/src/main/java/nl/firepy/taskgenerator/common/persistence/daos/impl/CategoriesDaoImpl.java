package nl.firepy.taskgenerator.common.persistence.daos.impl;

import lombok.extern.log4j.Log4j2;
import nl.firepy.taskgenerator.common.dto.CategoryDto;
import nl.firepy.taskgenerator.common.persistence.daos.CategoriesDao;
import nl.firepy.taskgenerator.common.persistence.entities.CategoryEntity;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Stateless
@TransactionManagement
public class CategoriesDaoImpl implements CategoriesDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<CategoryEntity> get(int id) {
        return Optional.ofNullable(em.find(CategoryEntity.class, id));
    }

    @Override public List<CategoryEntity> findByProject(ProjectEntity project) {
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

    @Override
    public void update(CategoryEntity entity) {
        em.merge(entity);
    }

    @Override
    public List<CategoryEntity> fetchFromList(ProjectEntity project, List<CategoryDto> categories) {
        List<CategoryEntity> entities = new ArrayList<>();
        for(CategoryDto category : categories) {
            var categoryEntityOptional = get(category.getId());
            if(categoryEntityOptional.isPresent()) {
                var categoryEntity = categoryEntityOptional.get();
                if(categoryEntity.getProject().getId() == project.getId()) {
                    entities.add(categoryEntity);
                } else {
                    log.warn("A request tried to add category from another project to a task");
                    throw new BadRequestException();
                }
            }
        }
        return entities;
    }
}
