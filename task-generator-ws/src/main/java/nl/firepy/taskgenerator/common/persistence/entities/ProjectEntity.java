package nl.firepy.taskgenerator.common.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.firepy.taskgenerator.common.dto.Dto;
import nl.firepy.taskgenerator.common.dto.ProjectDto;
import nl.firepy.taskgenerator.common.persistence.DtoConvertable;

import javax.persistence.*;

@Entity(name = "Project")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Project.findByAccount", query = "SELECT p FROM Project p WHERE p.owner = :account")
})
public class ProjectEntity implements DtoConvertable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String projectName;

    @ManyToOne(optional = false, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private AccountEntity owner;

    @Override
    public ProjectDto toDto() {
        return new ProjectDto(id, projectName, owner.toDto());
    }
}
