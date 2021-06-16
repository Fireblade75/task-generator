package nl.firepy.taskgenerator.common.persistence.daos;

import lombok.extern.log4j.Log4j2;
import nl.firepy.taskgenerator.common.persistence.entities.AccountEntity;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Log4j2
@Stateless
@TransactionManagement
public class AccountsDao implements BaseDao<AccountEntity> {

    @PersistenceContext
    private EntityManager em;

    public Optional<AccountEntity> findByMail(String email) {
        log.info("Handling login request for " + email);
        var query = em.createNamedQuery("Account.findByMail", AccountEntity.class);
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
        var query = em.createNamedQuery("Account.findByMail", AccountEntity.class);
        query.setParameter("email", email.toLowerCase());
        return query.getResultList().size() == 1;
    }

    @Override
    public Optional<AccountEntity> get(int id) {
        return Optional.ofNullable(em.find(AccountEntity.class, id));
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void save(AccountEntity account) {
        em.persist(account);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(int id) {
        get(id).ifPresentOrElse(em::remove,
                () -> { throw new NotFoundException(); });
    }
}
