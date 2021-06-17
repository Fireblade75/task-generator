package nl.firepy.taskgenerator.projects;

import lombok.extern.log4j.Log4j2;
import nl.firepy.taskgenerator.common.dto.ProjectDto;
import nl.firepy.taskgenerator.common.errors.exceptions.TaskGenAppException;
import nl.firepy.taskgenerator.common.persistence.daos.AccountsDao;
import nl.firepy.taskgenerator.common.persistence.daos.ProjectsDao;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;
import nl.firepy.taskgenerator.common.security.AuthData;
import nl.firepy.taskgenerator.common.security.annotations.RequiresRole;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/projects/")
@RequiresRole("user")
@RequestScoped
@Log4j2
public class ProjectsResource {

    @Inject
    private AuthData authData;

    @Inject
    private AccountsDao accountsDao;

    @Inject
    private ProjectsDao projectsDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RequiresRole("user")
    public List<ProjectDto> getMyProjects() {
        var account = accountsDao.findByMail(authData.getEmail());
        if(account.isPresent()) {
            return projectsDao.getProjectsByAccount(account.get())
                    .stream().map(ProjectEntity::toDto).collect(Collectors.toList());
        } else {
            throw new TaskGenAppException("User not found");
        }
    }

    public ProjectEntity getProject(int id) {
        var projectOptional = projectsDao.get(id);
        if(projectOptional.isEmpty()) {
            log.warn("Project with id " + id + " does not exist");
            throw new NotFoundException("Project with id " + id + " does not exist");
        }

        var project = projectOptional.get();
        if(!project.getOwner().getEmail().equals(authData.getEmail())) {
            log.warn("You are not the owner of the project");
            throw new NotAuthorizedException("You are not the owner of the project");
        }

        return project;
    }
}
