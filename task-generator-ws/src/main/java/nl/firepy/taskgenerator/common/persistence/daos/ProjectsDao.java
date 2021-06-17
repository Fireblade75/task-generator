package nl.firepy.taskgenerator.common.persistence.daos;

import nl.firepy.taskgenerator.common.persistence.entities.AccountEntity;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectsDao extends BaseDao<ProjectEntity> {
    List<ProjectEntity> getProjectsByAccount(AccountEntity account);

    @Override
    Optional<ProjectEntity> get(int id);

    @Override
    void save(ProjectEntity project);

    @Override
    void delete(int id);
}
