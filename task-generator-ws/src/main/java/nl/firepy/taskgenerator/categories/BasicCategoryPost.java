package nl.firepy.taskgenerator.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class BasicCategoryPost {
    @NotNull
    @Size(min = 1, max = 16)
    private String name;

    @NotNull
    @Size(min = 7, max = 7)
    private String color;

    @NotNull
    private int projectId;

}
