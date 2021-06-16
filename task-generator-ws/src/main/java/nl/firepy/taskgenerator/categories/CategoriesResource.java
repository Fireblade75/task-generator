package nl.firepy.taskgenerator.categories;

import nl.firepy.taskgenerator.common.dto.CategoryDto;
import nl.firepy.taskgenerator.common.persistence.daos.CategoriesDao;
import nl.firepy.taskgenerator.common.persistence.daos.ProjectsDao;
import nl.firepy.taskgenerator.common.persistence.entities.CategoryEntity;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;
import nl.firepy.taskgenerator.common.security.annotations.RequiresRole;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tasks/")
@RequiresRole("user")
@RequestScoped
public class CategoriesResource {

    @Inject
    private ProjectsDao projectsDao;

    @Inject
    private CategoriesDao categoriesDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public BasicCategoryResponse getCategories(@QueryParam("projectId") int projectId) {
        var projectOptional = projectsDao.get(projectId);
        if(projectOptional.isEmpty()) {
            throw new NotFoundException("Project with id " + projectId + " does not exist");
        }
        ProjectEntity project = projectOptional.get();
        List<CategoryEntity> categories = categoriesDao.findByProject(project);

        BasicCategoryResponse response = new BasicCategoryResponse(project.toDto());
        categories.forEach(response::addCategory);
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategory(BasicCategoryPost categoryPost) {
        var projectOptional = projectsDao.get(categoryPost.getProjectId());
        if(projectOptional.isEmpty()) {
            throw new NotFoundException("Project with id " + categoryPost.getProjectId() + " does not exist");
        }

        CategoryEntity category = CategoryEntity.builder()
                .name(categoryPost.getName())
                .color(categoryPost.getColor())
                .project(projectOptional.get())
                .build();

        categoriesDao.save(category);
        return Response.ok().build();
    }
}
