package nl.firepy.taskgenerator.common.persistence.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    private int id;

    private String name;

    private String description;
}
