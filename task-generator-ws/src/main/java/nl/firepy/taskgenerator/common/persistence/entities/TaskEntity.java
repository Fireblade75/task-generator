package nl.firepy.taskgenerator.common.persistence.entities;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Task")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Task.findByProject", query = "SELECT t FROM Task t WHERE t.project = :project")
})
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @ManyToOne(optional = false, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private ProjectEntity project;
}
