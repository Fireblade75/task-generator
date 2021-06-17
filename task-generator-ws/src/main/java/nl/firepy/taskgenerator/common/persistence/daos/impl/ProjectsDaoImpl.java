package nl.firepy.taskgenerator.common.persistence.daos.impl;

import lombok.extern.log4j.Log4j2;
import nl.firepy.taskgenerator.common.persistence.daos.ProjectsDao;
import nl.firepy.taskgenerator.common.persistence.entities.AccountEntity;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Log4j2
@Stateless
@TransactionManagement
public class ProjectsDaoImpl implements ProjectsDao {

    @PersistenceContext
    private EntityManager em;

    @Override public List<ProjectEntity> getProjectsByAccount(AccountEntity account) {
        var query = em.createNamedQuery("Project.findByAccount", ProjectEntity.class);
        query.setParameter("account", account);
        return query.getResultList();
    }

    @Override
    public Optional<ProjectEntity> get(int id) {
        return Optional.ofNullable(em.find(ProjectEntity.class, id));
    }

    @Override
    public void save(ProjectEntity project) {
        em.persist(project);
    }

    @Override
    public void delete(int id) {
        get(id).ifPresentOrElse(em::remove,
                () -> { throw new NotFoundException(); });
    }
}
