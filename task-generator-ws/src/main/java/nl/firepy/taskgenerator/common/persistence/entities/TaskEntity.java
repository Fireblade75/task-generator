package nl.firepy.taskgenerator.common.persistence.entities;

import lombok.*;
import nl.firepy.taskgenerator.common.dto.CategoryDto;
import nl.firepy.taskgenerator.common.dto.Dto;
import nl.firepy.taskgenerator.common.dto.TaskDto;
import nl.firepy.taskgenerator.common.persistence.DtoConvertable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Task")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Task.findByProject", query = "SELECT t FROM Task t WHERE t.project = :project")
})
public class TaskEntity implements DtoConvertable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 1, max = 32)
    private String name;

    @Size(max = 256)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<CategoryEntity> categories;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ProjectEntity project;

    private boolean done;

    @Override
    public TaskDto toDto() {
        List<CategoryDto> categoryList = categories.stream().map(CategoryEntity::toDto).collect(Collectors.toList());

        return TaskDto.builder()
                .id(id)
                .name(name)
                .description(description)
                .projectId(project.getId())
                .done(done)
                .categories(categoryList)
                .build();
    }
}
