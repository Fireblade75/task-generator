package nl.firepy.taskgenerator.categories;

import lombok.extern.log4j.Log4j2;
import nl.firepy.taskgenerator.common.dto.CategoryDto;
import nl.firepy.taskgenerator.common.persistence.daos.CategoriesDao;
import nl.firepy.taskgenerator.common.persistence.daos.ProjectsDao;
import nl.firepy.taskgenerator.common.persistence.entities.CategoryEntity;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;
import nl.firepy.taskgenerator.common.security.AuthData;
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

@Path("/categories/")
@RequiresRole("user")
@RequestScoped
@Log4j2
public class CategoriesResource {

    @Inject
    private AuthData authData;

    @Inject
    private ProjectsDao projectsDao;

    @Inject
    private CategoriesDao categoriesDao;

    @Inject
    private ProjectsResource projectsResource;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CategoryDto> getCategories(@QueryParam("projectId") int projectId) {
        var projectOptional = projectsDao.get(projectId);
        if(projectOptional.isEmpty()) {
            throw new NotFoundException("Project with id " + projectId + " does not exist");
        }
        ProjectEntity project = projectOptional.get();
        return categoriesDao.findByProject(project)
                .stream().map(CategoryEntity::toDto).collect(Collectors.toList());
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryDto deleteCategories(@QueryParam("categoryId") int categoryId) {
        CategoryEntity category = getCategory(categoryId);
        if(!category.getProject().getOwner().getEmail().equals(authData.getEmail())) {
            throw new NotAuthorizedException("You are not the owner of the project");
        }

        categoriesDao.delete(category.getId());
        return category.toDto();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(@Valid CategoryDto categoryPost) {
        ProjectEntity project = projectsResource.getProject(categoryPost.getProjectId());

        CategoryEntity category = CategoryEntity.builder()
                .name(categoryPost.getName())
                .color(categoryPost.getColor())
                .project(project)
                .build();

        categoriesDao.save(category);
        return Response.status(201).entity(category.toDto()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryDto updateCategory(@Valid CategoryDto categoryPost) {
        ProjectEntity project = projectsResource.getProject(categoryPost.getProjectId());
        CategoryEntity entity = getCategory(categoryPost.getId());

        entity.setColor(categoryPost.getColor());
        entity.setName(categoryPost.getName());

        categoriesDao.update(entity);

        return entity.toDto();
    }

    private CategoryEntity getCategory(int id) {
        var categoryOptional = categoriesDao.get(id);
        if(categoryOptional.isEmpty()) {
            log.warn("Category with id " + id + " does not exist");
            throw new NotFoundException("Category with id " + id + " does not exist");
        }
        return categoryOptional.get();
    }
}
