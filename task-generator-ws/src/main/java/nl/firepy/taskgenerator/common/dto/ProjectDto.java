package nl.firepy.taskgenerator.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto implements Dto {
    @NotNull
    private int id;

    @NotNull @NotBlank
    private String projectName;

    private UserDto owner;
}
