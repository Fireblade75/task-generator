package nl.firepy.taskgenerator.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto implements Dto {

    private int id;

    @NotNull
    @Size(min = 1, max = 16)
    private String name;

    @NotNull
    private ProjectDto project;
}
