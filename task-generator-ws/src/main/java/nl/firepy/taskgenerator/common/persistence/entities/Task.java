package nl.firepy.taskgenerator.common.persistence.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @ManyToOne(optional = false, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Project project;
}
