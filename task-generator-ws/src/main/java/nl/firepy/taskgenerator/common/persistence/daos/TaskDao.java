package nl.firepy.taskgenerator.common.persistence.daos;

import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;
import nl.firepy.taskgenerator.common.persistence.entities.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface TaskDao extends BaseDao<TaskEntity> {
    List<TaskEntity> findByName(String name);

    List<TaskEntity> findByProject(ProjectEntity project);

    @Override
    Optional<TaskEntity> get(int id);

    @Override
    void save(TaskEntity task);

    @Override
    void delete(int id);
}
