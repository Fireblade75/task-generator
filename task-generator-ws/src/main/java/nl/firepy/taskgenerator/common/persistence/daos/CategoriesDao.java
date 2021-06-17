package nl.firepy.taskgenerator.common.persistence.daos;

import nl.firepy.taskgenerator.common.dto.CategoryDto;
import nl.firepy.taskgenerator.common.persistence.entities.CategoryEntity;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;

import java.util.List;
import java.util.Optional;

public interface CategoriesDao extends BaseDao<CategoryEntity> {
    @Override
    Optional<CategoryEntity> get(int id);

    List<CategoryEntity> findByProject(ProjectEntity project);

    @Override
    void save(CategoryEntity category);

    @Override
    void delete(int id);

    void update(CategoryEntity entity);

    List<CategoryEntity> fetchFromList(ProjectEntity project, List<CategoryDto> categories);
}
