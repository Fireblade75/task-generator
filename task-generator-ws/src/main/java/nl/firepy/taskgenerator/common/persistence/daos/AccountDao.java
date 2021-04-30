package nl.firepy.taskgenerator.common.persistence.daos;

import lombok.extern.log4j.Log4j2;
import nl.firepy.taskgenerator.account.login.LoginRequest;
import nl.firepy.taskgenerator.common.persistence.entities.Account;

import javax.inject.Inject;
import java.util.Optional;
import java.util.logging.Logger;

@Log4j2
public class AccountDao extends BaseDao<Account> {

    public Optional<Account> findByMail(String email) {
        log.info("Handling login request for " + email);
        var query = em().createNamedQuery("Account.findByMail", Account.class);
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
        em().createQuery("SELECT a from Account a WHERE 1=1");

        var query = em().createNamedQuery("Account.findByMail", Account.class);
        query.setParameter("email", email.toLowerCase());
        return query.getResultList().size() == 1;
    }
}
