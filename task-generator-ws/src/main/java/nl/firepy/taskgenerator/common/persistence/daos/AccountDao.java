package nl.firepy.taskgenerator.common.persistence.daos;

import nl.firepy.taskgenerator.account.login.LoginRequest;
import nl.firepy.taskgenerator.common.persistence.entities.Account;

import java.util.Optional;

public class AccountDao extends BaseDao<Account> {

    public Optional<Account> match(LoginRequest credentials) {
        var query = em.createNamedQuery("Account.match", Account.class);
        query.setParameter("email", credentials.getEmail());
        query.setParameter("hash", credentials.getHash());
        var matches = query.getResultList();

        if(matches.size() > 0) {
            return Optional.of(matches.get(0));
        } else {
            return Optional.empty();
        }
    }

    public boolean containsEmail(String email) {
        em.createQuery("SELECT a from Account a WHERE 1=1");

        var query = em.createNamedQuery("Account.containsMail", Integer.class);
        query.setParameter("email", email);
        return query.getSingleResult() != 0;
    }
}
