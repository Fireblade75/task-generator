package nl.firepy.taskgenerator.projects;

import nl.firepy.taskgenerator.common.dto.ProjectDto;
import nl.firepy.taskgenerator.common.errors.exceptions.TaskGenAppException;
import nl.firepy.taskgenerator.common.persistence.daos.AccountsDao;
import nl.firepy.taskgenerator.common.persistence.daos.ProjectsDao;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;
import nl.firepy.taskgenerator.common.security.AuthData;
import nl.firepy.taskgenerator.common.security.annotations.RequiresRole;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
import java.util.stream.Collectors;

@Path("/tasks/")
@RequiresRole("user")
@RequestScoped
public class ProjectsResource {

    @Inject
    private AuthData authData;

    @Inject
    private AccountsDao accountsDao;

    @Inject
    private ProjectsDao projectsDao;

    @GET
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
}
