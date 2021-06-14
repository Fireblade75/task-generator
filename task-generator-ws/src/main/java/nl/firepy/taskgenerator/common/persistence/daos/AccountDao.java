package nl.firepy.taskgenerator.common.persistence.daos;

import lombok.extern.log4j.Log4j2;
import nl.firepy.taskgenerator.common.persistence.entities.Account;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Log4j2
@Stateless
@TransactionManagement
public class AccountDao implements BaseDao<Account> {

    @PersistenceContext
    private EntityManager em;

    public Optional<Account> findByMail(String email) {
        log.info("Handling login request for " + email);
        var query = em.createNamedQuery("Account.findByMail", Account.class);
        query.setParameter("email", email.toLowerCase());
//        query.setParameter("hash", credentials.getHash());
        var matches = query.getResultList();

        if(matches.size() > 0) {
            return Optional.of(matches.get(0));
        } else {
            return Optional.empty();
        }
    }

    public boolean containsEmail(String email) {
        em.createQuery("SELECT a from Account a WHERE 1=1");

        var query = em.createNamedQuery("Account.findByMail", Account.class);
        query.setParameter("email", email.toLowerCase());
        return query.getResultList().size() == 1;
    }

    @Override
    public Optional<Account> get(int id) {
        return Optional.ofNullable(em.find(Account.class, id));
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void save(Account account) {
        em.persist(account);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(int id) {
        get(id).ifPresentOrElse(em::remove,
                () -> { throw new NotFoundException(); });
    }
}
