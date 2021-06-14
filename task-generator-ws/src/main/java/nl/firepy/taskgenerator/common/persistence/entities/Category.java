package nl.firepy.taskgenerator.common.persistence.entities;

import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Project project;
}
