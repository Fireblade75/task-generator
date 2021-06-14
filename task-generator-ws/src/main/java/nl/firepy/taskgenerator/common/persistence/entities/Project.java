package nl.firepy.taskgenerator.common.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Project.findByAccount", query = "SELECT p FROM Project p WHERE p.owner = :account")
})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String projectName;

    @ManyToOne(optional = false, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Account owner;
}
