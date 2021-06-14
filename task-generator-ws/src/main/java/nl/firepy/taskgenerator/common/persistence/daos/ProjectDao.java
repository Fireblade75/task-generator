package nl.firepy.taskgenerator.common.persistence.daos;

import nl.firepy.taskgenerator.common.persistence.entities.Account;
import nl.firepy.taskgenerator.common.persistence.entities.Project;
import nl.firepy.taskgenerator.tasks.Task;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Stateless
public class ProjectDao implements BaseDao<Project> {

    @PersistenceContext
    private EntityManager em;

    public List<Project> getProjectsByAccount(Account account) {
        var query = em.createNamedQuery("Project.findByAccount", Project.class);
        query.setParameter("account", account);
        return query.getResultList();
    }

    @Override
    public Optional<Project> get(int id) {
        return Optional.ofNullable(em.find(Project.class, id));
    }

    @Override
    public void save(Project project) {
        em.persist(project);
    }

    @Override
    public void delete(int id) {
        get(id).ifPresentOrElse(em::remove,
                () -> { throw new NotFoundException(); });
    }
}
