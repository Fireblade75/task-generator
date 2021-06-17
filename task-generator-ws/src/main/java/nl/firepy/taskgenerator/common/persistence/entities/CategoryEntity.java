package nl.firepy.taskgenerator.common.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.firepy.taskgenerator.common.dto.CategoryDto;
import nl.firepy.taskgenerator.common.dto.Dto;
import nl.firepy.taskgenerator.common.persistence.DtoConvertable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "Category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Category.findByProject", query = "SELECT c FROM Category c WHERE c.project = :project")
})
public class CategoryEntity implements DtoConvertable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ProjectEntity project;
    
    @NotNull
    @Column(length = 16)
    private String name;

    @NotNull
    @Column(length = 7)
    private String color;

    @Override
    public CategoryDto toDto() {
        return new CategoryDto(id, name, color, project.getId());
    }
}
