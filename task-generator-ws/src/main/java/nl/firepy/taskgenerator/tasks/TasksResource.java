package nl.firepy.taskgenerator.tasks;

import nl.firepy.taskgenerator.common.dto.TaskDto;
import nl.firepy.taskgenerator.common.persistence.daos.CategoriesDao;
import nl.firepy.taskgenerator.common.persistence.daos.TaskDao;
import nl.firepy.taskgenerator.common.persistence.entities.CategoryEntity;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;
import nl.firepy.taskgenerator.common.persistence.entities.TaskEntity;
import nl.firepy.taskgenerator.common.security.annotations.RequiresRole;
import nl.firepy.taskgenerator.projects.ProjectsResource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/tasks/")
@RequiresRole("user")
@RequestScoped
public class TasksResource {

    @Inject
    private TaskDao taskDao;

    @Inject
    private ProjectsResource projectsResource;

    @Inject
    private CategoriesDao categoriesDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TaskDto> getTasks(@QueryParam("projectId") int projectId) {
        ProjectEntity project = projectsResource.getProject(projectId);
        return taskDao.findByProject(project).stream()
                .map(TaskEntity::toDto).collect(Collectors.toList());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TaskDto addTask(@Valid TaskDto taskDto) {
        ProjectEntity project = projectsResource.getProject(taskDto.getProjectId());
        List<CategoryEntity> categories = categoriesDao.fetchFromList(project, taskDto.getCategories());

        TaskEntity task = TaskEntity.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .done(taskDto.isDone())
                .categories(categories)
                .project(project)
                .build();

        taskDao.save(task);
        return task.toDto();
    }

}
