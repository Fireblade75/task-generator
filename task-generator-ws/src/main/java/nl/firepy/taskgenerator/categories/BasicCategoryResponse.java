package nl.firepy.taskgenerator.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.firepy.taskgenerator.common.dto.ProjectDto;
import nl.firepy.taskgenerator.common.persistence.entities.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Deprecated
public class BasicCategoryResponse {
    private ProjectDto project;
    private List<BasicCategory> categories = new ArrayList<>();

    public BasicCategoryResponse(ProjectDto project) {
        this.project = project;
    }

    public void addCategory(int id, String name, String color) {
        this.categories.add(new BasicCategory(id, name, color));
    }

    public void addCategory(CategoryEntity entity) {
        this.addCategory(entity.getId(), entity.getName(), entity.getColor());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasicCategory {
        private int id;
        private String name;
        private String color;
    }
}
