package nl.firepy.taskgenerator.common.persistence.daos;

import nl.firepy.taskgenerator.common.persistence.entities.AccountEntity;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Optional;

public interface AccountsDao extends BaseDao<AccountEntity> {
    Optional<AccountEntity> findByMail(String email);

    boolean containsEmail(String email);

    @Override
    Optional<AccountEntity> get(int id);

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    void save(AccountEntity account);

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    void delete(int id);
}
