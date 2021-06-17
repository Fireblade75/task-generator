package nl.firepy.taskgenerator.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto implements Dto {

    private int id;

    @NotNull
    @Size(min = 1, max = 32)
    private String name;

    @NotNull
    @Size(max = 256)
    private String description;

    @NotNull
    private List<CategoryDto> categories;

    @NotNull
    private int projectId;

    private boolean done = false;
}
